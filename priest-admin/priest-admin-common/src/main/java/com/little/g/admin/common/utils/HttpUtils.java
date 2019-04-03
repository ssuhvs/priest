package com.little.g.admin.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lengligang on 16/2/25.
 */
public class HttpUtils {


    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();



    static {
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
    }

    private static CloseableHttpClient createHttpclient() {
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, null, "UTF-8");
    }

    public static String get(String url) {
        return get(url, null, null, "UTF-8");
    }


    public static String get(String url, Map<String, String> params, Map<String, String> header, String charset) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpGet get = null;
        try {
            get = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                get.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }

        return getResponse(get, charset);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> header, String charset) {
        HttpPost post = createPost(url);

        setHeader(header, post);
        if (params != null && params.size() > 0) {
            List<NameValuePair> formparams = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity;
            try {
                formEntity = new UrlEncodedFormEntity(formparams, charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            post.setEntity(formEntity);
        }

        return getResponse(post, charset);
    }

    private static HttpPost createPost(String url) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpPost post;
        try {
            post = new HttpPost(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    public static String post(String url, String body) {
        return post(url, null, body, "UTF-8");
    }


    public static String post(String url, Map<String, String> header, String body, String charset) {
        HttpPost post = createPost(url);
        setHeader(header, post);
        if (!StringUtils.isEmpty(body)) {
            StringEntity bodyEntity = new StringEntity(body, charset);
            post.setEntity(bodyEntity);
        }
        return getResponse(post, charset);
    }

    private static void setHeader(Map<String, String> header, HttpPost post) {
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                post.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
    }


    private static String getResponse(HttpUriRequest request, String charset) {
        try {
            CloseableHttpClient client = createHttpclient();
            CloseableHttpResponse response = client.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, charset);
            } finally {
                //response.close();
                // client.close();
            }
        } catch (IOException e) {
            //TODO:是否协议通知上层
            throw new RuntimeException(e);
        }
    }
}
