package ${packageName}.api;

import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import ${packageName}.dto.${entityName}DTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
* Created by ${author} on 2019/3/9.
*/
public interface ${entityName}Service {
/**
* 添加
* @param entity
* @return
*/
boolean add(@Valid ${entityName}DTO entity);

/**
* 根据id获取
* @param id
* @return
*/
${entityName}DTO get(@NotBlank Integer id);

/**
* 更新
* @param entity
* @return
*/
boolean update(@Valid ${entityName}DTO entity);

/**
* 删除
* @param id
* @return
*/
boolean delete(@NotBlank Integer id);

/**
* 增量查询
* @param param
* @return
*/
ListResultDTO<${entityName}DTO> list(@NotBlank TimeQueryParam param);

}
