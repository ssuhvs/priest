#!/usr/bin/env bash

module_name="User"
module_package="com/little/g/user"

project="priest-user"
project_dao=${project}"-dao"
project_api=${project}"-api"
project_http=${project}"-http"
root_dict=`pwd`
mvn clean install
cd ./${project_dao}
mvn mybatis-generator:generate
source_java=src/main/java/${module_package}/model/${module_name}.java
target_java=${root_dict}/${project_api}/src/main/java/${module_package}/dto/${module_name}DTO.java
echo "cp java object from  ${source_java} to ${target_java}"
cp -Rf ./src/main/java/${module_package}/model/${module_name}.java  ${root_dict}/${project_api}/src/main/java/${module_package}/dto/${module_name}DTO.java
pkg=`echo ${module_package//\//.}`".dto"
sed -i  "" "s/^package.*;/package ${pkg};/" ${target_java}
sed -i "" "s/${module_name}/${module_name}DTO/" ${target_java}
cd ${root_dict}/${project_api}
mvn generator:generate
cd ${root_dict}/${project_http}
mvn generator:generate
echo "generate comple!"






