package com.little.g;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "generate",requiresDirectInvocation = true)
public class GenerateMojo
        extends AbstractMojo {

    @Parameter(property ="project.build.sourceDirectory" )
    private File source;

    @Parameter(property ="project.basedir" )
    private File baseDir;

    @Parameter(defaultValue = "/src/main/conf")
    private String config;

    @Parameter(alias = "tplPath",defaultValue = "/tpl")
    private String tplPath;
    /**
     *
     */
    @Parameter(required = true)
    private  File configurationFile;

    @Parameter(alias = "overwrite")
    private boolean overwrite;



    @Parameter(defaultValue = "/com/little/g/test")
    private String packagePath;


    private static Map<String,Object> properties=new HashMap<String, Object>();





    public void execute()
            throws MojoExecutionException {


        String realPath=baseDir.getPath()+config+tplPath;

        File tplPathFile=new File(realPath);
        if(!tplPathFile.exists()){
            throw new RuntimeException("tplpath is not exist path:"+realPath);
        }
        if(!configurationFile.exists()){
            throw new RuntimeException("configurationFile is not exist path:"+configurationFile.getPath());
        }
        getLog().info("print generator tplpath:"+realPath);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document=null;
        try {
            builder = builderFactory.newDocumentBuilder();
            document=builder.parse(configurationFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //解析全局属性
        NodeList propertiesList=document.getElementsByTagName ("properties");
        if(propertiesList != null && propertiesList.getLength()>0){
            Node proNode=propertiesList.item(0);
            if(proNode != null) {
                NodeList propertieNodes=proNode.getChildNodes();
                if(propertieNodes != null && propertieNodes.getLength()>0){
                    Map<String,Object>  properMap=extractProperties(propertieNodes);
                    if(properMap != null ){
                        properties.putAll(properMap);
                    }
                }
            }
        }

        //解析生成配置
        List<GenerateConf> generateConfs=new ArrayList<GenerateConf>();

        NodeList  generateFileNodes=document.getElementsByTagName ("generateFile");
        if(generateFileNodes != null && generateFileNodes.getLength()>0){
            for(int i=0;i<generateFileNodes.getLength();i++){
                GenerateConf conf=new GenerateConf();
                Node generateFileNode=generateFileNodes.item(i);
                NamedNodeMap attributes=generateFileNode.getAttributes();
                //属性设置到类
                Node packagePathNode=attributes.getNamedItem("packagePath");
                if(packagePathNode != null && StringUtils.isNotEmpty(packagePathNode.getNodeValue())){
                    conf.setPackagePath(packagePathNode.getNodeValue());
                }else {
                    conf.setPackagePath(packagePath);
                }

                Node templateNameNode=attributes.getNamedItem("templateName");
                if(templateNameNode != null && StringUtils.isNotEmpty(templateNameNode.getNodeValue())){
                    conf.setTemplateName(templateNameNode.getNodeValue());
                }else {
                    throw new RuntimeException("templateName can not be empty!");
                }

                Node fileNameNode=attributes.getNamedItem("fileName");
                if(fileNameNode != null && StringUtils.isNotEmpty(fileNameNode.getNodeValue())){
                    conf.setFileName(fileNameNode.getNodeValue());
                }else {
                    throw new RuntimeException("fileName can not be empty!");
                }


                NodeList dataNodes=generateFileNode.getChildNodes();
                if(dataNodes != null && dataNodes.getLength()>0){
                    Map<String, Object> dataMap=new HashMap<String, Object>();
                    dataMap.putAll(properties);
                    Map<String, Object> propertiesMap = extractProperties(dataNodes);
                    if(dataMap !=null){
                        dataMap.putAll(propertiesMap);
                    }
                    conf.setDataMap(dataMap);
                }
                generateConfs.add(conf);
            }

        }

        if(generateConfs == null || generateConfs.size()<=0){
            getLog().warn("no generateFile config find, generate file end!");
            return;
        }

        for(GenerateConf generateConf:generateConfs){
            String codePath=source.getPath()+generateConf.getPackagePath();
            File codePathFile=new File(codePath);
            if(!codePathFile.exists()){
                codePathFile.mkdirs();
                getLog().info("create generator code path:{}"+codePath);
            }

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            Writer out = null;
            try {
                // step2 获取模版路径
                configuration.setDirectoryForTemplateLoading(new File(realPath));
                // step3 创建数据模型

                // step4 加载模版文件
                Template template = configuration.getTemplate(generateConf.getTemplateName());
                // step5 生成数据
                File docFile = new File(codePath + "/" + generateConf.getFileName());
                if(docFile.exists()){
                    if(!overwrite) {
                        getLog().warn("file already exist skip file generate file:" + docFile.getPath());
                        continue;
                    }
                    docFile.delete();
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
                // step6 输出文件
                template.process(generateConf.getDataMap(), out);

                getLog().info("file generate complete! file path:"+docFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != out) {
                        out.flush();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }

    private Map<String, Object> extractProperties(NodeList dataNodes) {
        if(dataNodes == null || dataNodes.getLength()<=0){
            return null;
        }
        Map<String,Object> dataMap=new HashMap<String, Object>();
        for(int j=0;j<dataNodes.getLength();j++){
            Node property = dataNodes.item(j);
            if(!"property".equals(property.getNodeName())){
                continue;
            }
            NamedNodeMap dataAttributs=property.getAttributes();
            dataMap.put(dataAttributs.getNamedItem("name").getNodeValue(),dataAttributs.getNamedItem("value").getNodeValue());
        }
        return dataMap;
    }
}
