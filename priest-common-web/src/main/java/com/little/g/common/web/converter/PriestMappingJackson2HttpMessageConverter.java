package com.little.g.common.web.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * the specific message converter which support content-length of result json
 * Created by wangzhen on 12/2/15.
 */
public class PriestMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static final String JSON_IGNORE_NULL = "json_ignore_null";

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        process(object, type, outputMessage);
    }

    private void process(Object object, Type type, HttpOutputMessage outputMessage) throws IOException {
        // set content length if result json exist
        if (outputMessage != null) {
            if (object != null) {
//                if (object instanceof ResultJson) {
                    final HttpHeaders headers = outputMessage.getHeaders();
                    JsonEncoding encoding = getJsonEncoding(headers.getContentType());

                    // 根据输出内容动态计算content-length
//                    String source = object.toString();
                    String source = objectMapper.writeValueAsString(object);
                    int length = getContentLength(source);
                    headers.setContentLength(length);

                    // local reference
                    ObjectMapper objectMapper = this.objectMapper;

                    // 根据头信息确定是否忽略 null json节点,空字符串不处理
                    String jsonIgnoreNull = headers.getFirst(JSON_IGNORE_NULL);
                    if (!Strings.isNullOrEmpty(jsonIgnoreNull)
                            && jsonIgnoreNull.equalsIgnoreCase(String.valueOf(JSON_IGNORE_NULL_ENUM.ENABLE.value()))) {
                        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    }

                    JsonGenerator generator = objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
                    try {
                        writePrefix(generator, object);
                        Class<?> serializationView = null;
                        Object value = object;
                        if (value instanceof MappingJacksonValue) {
                            MappingJacksonValue container = (MappingJacksonValue) object;
                            value = container.getValue();
                            serializationView = container.getSerializationView();
                        }
                        if (serializationView != null) {
                            objectMapper.writerWithView(serializationView).writeValue(generator, value);
                        } else {
                            objectMapper.writeValue(generator, value);
                        }
                        writeSuffix(generator, object);
                        generator.flush();

                    } catch (JsonProcessingException ex) {
                        throw new HttpMessageNotWritableException("Could not write content: " + ex.getMessage(), ex);
                    }
//                }
            }
        }
    }

    /**
     * utf8mb4 每个字符差四个长度, 统计数量*8 Character.charCount()
     *
     * @param source 元字符串
     * @return HTTP CONTENT-LENGTH
     */
    private int getContentLength(String source) {
        if (Strings.isNullOrEmpty(source)) {
            return 0;
        }

        int wcharCount = 0;

        for (int i = 0; i < source.length(); i++) {
            int count = Character.charCount(source.codePointAt(i));
            if (count == 2) {
                ++wcharCount;
            }
        }

        int length = source.getBytes(Charset.forName("UTF-8")).length;
        length += wcharCount * 8;
        return length;
    }

    private enum JSON_IGNORE_NULL_ENUM {
        ENABLE(1),
        DISABLE(0);

        private int code;

        JSON_IGNORE_NULL_ENUM(int code) {
            this.code = code;
        }

        public int value() {
            return this.code;
        }
    }
}
