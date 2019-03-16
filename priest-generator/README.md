# priest-generator

## 项目介绍

    priest-generator 是一个以freemarker为模板引擎的代码生成maven插件
    
## 开始使用

   1. 项目插件引入
   
   ```
        <plugin>
                    <groupId>com.little.g</groupId>
                    <artifactId>generator-maven-plugin</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                    <configuration>
                        <tplPath>/tpl</tplPath>   //模板存放路径 非必填 默认位于src/main/conf/tpl 目录下
                        <configurationFile>${project.basedir}/src/main/conf/GenerateConfig.xml</configurationFile>  //生成配置所在目录
                        <overwrite>false</overwrite>
                    </configuration>
         </plugin>
    
   ```
    
   2. 书写模板引擎 ,模板引擎基于freemarker，支持freemarker所有语法 文档参见 [Freemarker](https://freemarker.apache.org/docs/)
   
   例如:
   
   ```
        package ${packageName}.service;
                    
        import com.little.g.common.dto.ListResultDTO;
        import com.little.g.common.params.TimeQueryParam;
        import ${packageName}.api.${entityName}Service;
        import ${packageName}.dto.${entityName}DTO;
        import ${packageName}.mapper.${entityName}Mapper;
        import ${packageName}.model.${entityName};
        import ${packageName}.model.${entityName}Example;
        import org.springframework.beans.BeanUtils;
        import org.springframework.stereotype.Service;
        import org.springframework.util.CollectionUtils;
        
        import javax.annotation.Resource;
        import java.util.List;
        import java.util.stream.Collectors;
        
        /**
        * Created by ${author} on 2019/3/9.
        */
        @Service("${entityName?uncap_first}Service")
        public class ${entityName}ServiceImpl implements ${entityName}Service {
            @Resource
            private ${entityName}Mapper ${entityName?uncap_first}Mapper;
        
            @Override
            public boolean add(${entityName}DTO entity) {
                ${entityName} ${entityName?uncap_first}=new ${entityName}();
                BeanUtils.copyProperties(entity,${entityName?uncap_first});
                return ${entityName?uncap_first}Mapper.insertSelective(${entityName?uncap_first})>0;
            }
        
            @Override
            public ${entityName}DTO get(Integer id) {
                ${entityName} ${entityName?uncap_first}=${entityName?uncap_first}Mapper.selectByPrimaryKey(id);
                if(${entityName?uncap_first} == null){
                    return null;
                }
                ${entityName}DTO dto=new ${entityName}DTO();
                BeanUtils.copyProperties(${entityName?uncap_first},dto);
                return dto;
            }
        
            @Override
            public boolean update(${entityName}DTO entity) {
                if(entity.getId() == null) return false;
                ${entityName} ${entityName?uncap_first}=new ${entityName}();
                BeanUtils.copyProperties(entity,${entityName?uncap_first});
                return ${entityName?uncap_first}Mapper.updateByPrimaryKeySelective(${entityName?uncap_first})>0;
            }
        
            @Override
            public boolean delete(Integer id) {
                return ${entityName?uncap_first}Mapper.deleteByPrimaryKey(id)>0;
            }
        
            @Override
            public ListResultDTO<${entityName}DTO> list(TimeQueryParam param) {
                ListResultDTO<${entityName}DTO> result=param.getResult(ListResultDTO.class);
        
                    ${entityName}Example example = new ${entityName}Example();
                    example.or().andCreateTimeLessThan(param.getLast());
                    example.setOrderByClause(String.format("create_time desc limit %d",result.getLimit()));
                    List<${entityName}> list= ${entityName?uncap_first}Mapper.selectByExample(example);
                    if(CollectionUtils.isEmpty(list)){
                        return result;
                    }
                    result.setLast(list.get(list.size()-1).getCreateTime());
                    result.setList(list.stream().map(entity->{
                    ${entityName}DTO dto=new ${entityName}DTO();
                    BeanUtils.copyProperties(entity,dto);
                    return dto;
                    }).collect(Collectors.toList()));
        
                    return result;
                }
        
        }
            
   
   ```
    
    
   3. 文件生成配置
    
```

    <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE generatorConfiguration[
                <!ELEMENT generatorConfiguration (properties,generateFile+) >
                <!ELEMENT properties (property*) >
                <!ELEMENT property  EMPTY>
                <!ELEMENT generateFile (property)>
                <!ATTLIST property
                name    CDATA #REQUIRED
                value   CDATA #REQUIRED >
                <!ATTLIST generateFile
                packagePath    CDATA #IMPLIED
                templateName   CDATA #REQUIRED
                fileName   CDATA #REQUIRED>
        ]>
        
        <generatorConfiguration>
            <!--全局属性 用于替换 generateFile 未配置的tpl 参数-->
            <properties>
                <property name="packageName" value="com.little.g.demo" />
                <property name="author" value="lengligang" />
            </properties>
            <!--
                文件生成配置
                packagePath:文件保存目录相对于java source directory 
                templateName:模板文件名
                fileName:生成文件名 
                property:对应freemarker模板属性名和替换值
                
            -->
            <generateFile packagePath="/com/little/g/demo/service" templateName="ServiceImpl.tpl" fileName="OrderServiceImpl.java">
                <property name="entityName" value="Order" />
            </generateFile>
        
        </generatorConfiguration>

```
    
    
    
    
    
## 在线文档

   *  [Freemarker](https://freemarker.apache.org/docs/) 
    