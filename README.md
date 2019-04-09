# priest
 dubbo mybatis springboot base soa rest api framework with customer code generator


## 前言


   `priest`项目是一个以 **incubator-dubbo** - **Spring Boot** - **Mybatis3** 为基础的SOA开源开发框架
   
## 项目介绍
   
   `priest`项目基于springboot+dubbo+mybatis的分布式敏捷开发框架，将 **JSR303** **hibernate-validate** 验证体系完美融合**dubbo**服务框架。并以此为基础开发了maven-code-generator 插件，让研发人员从重复的增删改查工作中彻底解脱。
 
   本项目为后续开源的所有项目的基石项目，后续计划陆续的开源项目将会有 **用户中心**  **支付中心** 敬请期待！
   

## 更新日志

2019-04-09
 
* 增加了用户模块
	1. 短信注册
	2. 用户信息补充
	3. 获取用户信息
	4. restApi 接口调用认证
	5. token 过期刷新
	6. 用户主动登出

* 增加后台管理模块
	1. 权限管理
	2. 菜单管理
	3. 管理员管理
	4. 代码生成demo 
	
* 代码生成插件
	1. 后台管理jsp代码生成支持
	2. 代码生成 list attribute 标签支持

## 项目计划


| 模块 | 功能描述 | 完成度 |
| --- | --- | --- |
| priest-pay | 支付宝支付  |  🔘  |
| priest-pay | 微信支付  |  🔘  |
| priest-pay | 用户账户  |  🔘  |
| dubbo-extend | 改为dubbo 原生JSR303 参数验证 |  🔘  |
| priest-user | token 生成 |  ✔️  |
| priest-user | restApi token 验证 |  ✔️  |
| priest-generator | 管理后台页面自动生成 |  ✔️  |

   

### 组织结构


```lua

├── dubbo
│   ├── assembly     --dubbo 打包相关配置
│   └── bin          --dubbo 启动脚本
├── dubbo-extend     --dubbo 扩展支持dubbo接口参数校验
├── plugin-test      --代码生成插件测试项目
├── priest-admin     --管理后台项目
│   ├── priest-admin-api     --管理后台api项目
│   ├── priest-admin-common  --管理后台公用模块
│   ├── priest-admin-dao     --管理后台数据库访问层
│   ├── priest-admin-http    --管理后台WEB界面
│   └── priest-admin-service --管理后台dubbo服务
├── priest-common            --priest 项目公用模块
├── priest-common-web        --priest 项目公用WEB模块
├── priest-demo              --priest 服务化样例项目
│   ├── priest-demo-api      --priest 服务化样例api项目
│   ├── priest-demo-dao      --priest 服务化样例dao项目
│   ├── priest-demo-http     --priest 服务化样例http项目
│   └── priest-demo-service  --priest 服务化样例dubbo服务项目
├── priest-generator         --priest 代码生成插件项目
├── priest-user              --用户项目
│   ├── priest-user-api      --用户api项目
│   ├── priest-user-dao		 --用户数据库访问层
│   ├── priest-user-http     --用户WEB接口
│   ├── priest-user-service  --用户dubbo服务
│   └── priest-user-token    --用户token生成模块
└── wiki_images              --wiki 引用图片
  
```

## Getting Started

-------

### 项目依赖
1. mysql 数据库
2. zookeeper 注册服务
3. maven
4. jdk 1.8+

### 项目编译

1. 进入项目根目录
2. 打开项目 priest-demo/priest-demo-dao/src/main/resources/demo.sql 建立创建测试数据库及表
3. 修改 priest-demo/priest-demo-dao/pom.xml develop profile 关于jdbc的配置见下图
	
	![](wiki_images/pom_mysql_conf.png)
	
4. 修改 根目录pom.xml develop profile 关于zookeeper配置见下图  
	![](wiki_images/pom_zookeeper_conf.png) 
	
5. 进入项目根目录
6. `mvn clean install -Pdevelop`

### 项目运行

1. dubbo service 启动

 运行  `priest-demo/priest-demo-service/src/test/java/com/little/g/demo/TestDubbo.java main`
 
2. http 启动
	
   * 进入priest-demo/priest-demo-http 项目目录 
   * 执行 `mvn spring-boot:run`  ，观察控制台日志输出，出现如下日志，便是启动成功了。

	![](wiki_images/tomcat_start_log.png) 

3. 接口访问测试

  `curl http://127.0.0.1:8888/user/test`



### 开发流程

#### 数据库建表
本例以order表为例子建表语句如下:

```sql
	
CREATE TABLE `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `money` bigint(15) DEFAULT NULL COMMENT '金额',
  `create_time` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
	
```


#### dao 生成

1. 打开priest-demo/priest-demo-dao/src/test/resources/generatorConfig.xml追加如下配置

```
<table tableName="order" delimitIdentifiers="true" >
            <generatedKey column="id"  sqlStatement="JDBC" />
</table>     
```

2.  调用priest-demo-dao的 mybatis-generator插件的  mybatis-generator:generate 任务,执行结果如下:
![](wiki_images/mybatis_generate_log.png) 

3. 关于mybatis-generator的详细配置和文档可参见 [MyBatis Generator](http://www.mybatis.org/generator/index.html)

#### api 生成

1. 拷贝 `com.little.g.demo.model.Order` 至 priest-demo-api 项目的 `com.little.g.demo.api.dto` 重命名为 OrderDTO
2. 打开priest-demo/priest-demo-api/src/main/conf/GenerateConfig.xml 追加如下配置

```
<generateFile packagePath="/com/little/g/demo/api" templateName="Service.tpl" fileName="OrderService.java">
        <property name="entityName" value="Order" />
</generateFile>

```

3.  调用priest-demo-api的 generator插件的 generator:generate 任务,执行结果如下:
![](wiki_images/generator_generate_log.png) 

4. 关于generator 插件的详细配置和文档可参见

#### service 生成

1. 打开priest-demo/priest-demo-service/src/main/conf/GenerateConfig.xml 追加如下配置

```
<generateFile packagePath="/com/little/g/demo/service" templateName="ServiceImpl.tpl" fileName="OrderServiceImpl.java">
        <property name="entityName" value="Order" />
</generateFile>

```

2.  调用priest-demo-service的 generator插件的 generator:generate 任务,执行结果如下:
![](wiki_images/service_generate.png) 

3. priest-demo-service dubbo-config.xml 追加dubbo service 接口暴露

	`<dubbo:service interface="com.little.g.demo.api.OrderService" ref="orderService"/>`

4. 关于generator 插件的详细配置和文档可参见 [priest generator](https://github.com/G-little/priest/tree/master/priest-generator)


#### http 生成

1. 打开priest-demo/priest-demo-http/src/main/conf/GenerateConfig.xml 追加如下配置

```
<generateFile packagePath="/com/little/g/demo/web" templateName="Controller.tpl" fileName="OrderController.java">
        <property name="entityName" value="Order" />
</generateFile>

```

2.  调用priest-demo-http的 generator插件的 generator:generate 任务,执行结果如下:
![](wiki_images/controller_generate.png) 

3. priest-demo-http dubbo-consume.xml 追加dubbo service 引用

	`<dubbo:reference id="orderService" interface="com.little.g.demo.api.OrderService" />`

4. 关于generator 插件的详细配置和文档可参见 [priest generator](https://github.com/G-little/priest/tree/master/priest-generator)


#### 管理后台代码生成

管理后台的dao、api和service 的生成与restApi的生成逻辑本质没有太大区别，只是在接口原有逻辑的基础上增加了分页逻辑，对于既有接口逻辑又有后台管理的模块，可以采用管理后台的 api、service模板
进行生成，下面只单独列出 admin 管理页面的代码生成配置：

1. 打开priest-admin/priest-admin-http/src/main/conf/GenerateConfig.xml 追加如下配置

**注意** :默认的web文件生成路径位于 WEB-INF/jsp+${webPath} 若要修改web文件生成路径，直接修改插件的webSource配置

```

<!-- Controller 生成配置 -->
<generateFile packagePath="/com/little/g/admin/web/controllers/test" templateName="Controller.tpl" fileName="BookController.java">
        <property name="packageName" value="com.little.g.admin.web.controllers.test" />
        <property name="basePackage" value="com.little.g.admin" />
        <property name="entityName" value="Book" />
        <property name="module" value="BOOK" />
        <property name="uri" value="/book" />
    </generateFile>
    
<!-- list.jsp 生成配置 -->
<generateFile webPath="/book" templateName="list.tpl" fileName="book-list.jsp">
        <property name="uri" value="/book" />
        <property name="module" value="图书" />
        <list name="attributes">
            <attribute name="name" required="true" comment="书名" />
            <attribute name="price" required="false" comment="价格" />
            <attribute name="author" required="false" comment="作者" />
            <attribute name="publisher" required="false" comment="出版商" />
        </list>
    </generateFile>

<!-- edit.jsp 生成配置 -->
<generateFile webPath="/book" templateName="edit.tpl" fileName="book-edit.jsp">
        <property name="uri" value="/book" />
        <property name="paramName" value="book" />
        <property name="module" value="图书" />
        <list name="attributes">
            <attribute name="name" required="true" comment="书名" />
            <attribute name="price" required="false" comment="价格" />
            <attribute name="author" required="false" comment="作者" />
            <attribute name="publisher" required="false" comment="出版商" />
        </list>
    </generateFile>
```

2.  调用priest-admin-http的 generator插件的 generator:generate 任务,执行结果如下:
![](wiki_images/admin-generate.png) 

3. priest-admin-http dubbo-consume.xml 追加dubbo service 引用

	`<dubbo:reference id="orderService" interface="com.little.g.demo.api.BookService" />`
	
4. 访问admin项目增加菜单

![](wiki_images/add-menu.png) 

5. admin 生成界面效果截图

列表页:

![](wiki_images/admin-list.png) 

修改页:

![](wiki_images/admin-add.png) 


6. 关于generator 插件的详细配置和文档可参见 [priest generator](https://github.com/G-little/priest/tree/master/priest-generator)








#### 批量生成脚本

您也可以在配置完成后，直接执行批量生成脚本，完成上述所有步骤
脚本位于 priest-demo 根目录

```shell

	sh	code_generate.sh
	
```

#### 最后项目重新编译运行，新开发的接口就可以测试了







   

 
 


