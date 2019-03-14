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
            List<${entityName}> list= ${entityName}Mapper.selectByExample(example);
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
