package com.little.g;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lengligang on 2019/3/13.
 */
public class GenerateConf implements Serializable {

    private String packagePath;

    private String webPath;

    private String templateName;

    private String fileName;

    private Map<String,Object> dataMap;


    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GenerateConf{");
        sb.append("packagePath='").append(packagePath).append('\'');
        sb.append(", webPath='").append(webPath).append('\'');
        sb.append(", templateName='").append(templateName).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", dataMap=").append(dataMap);
        sb.append('}');
        return sb.toString();
    }
}
