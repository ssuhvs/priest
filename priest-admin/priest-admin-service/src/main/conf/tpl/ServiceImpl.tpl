package ${packageName}.service.impl;

import com.little.g.admin.common.page.Page;
import com.little.g.common.params.PageQueryParam;
import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import ${packageName}.service.${entityName}Service;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.mapper.${entityName}Mapper;
import ${packageName}.model.${entityName};
import ${packageName}.model.${entityName}Example;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;

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

    @Override
    public Page<${entityName}DTO> pageList(@NotBlank PageQueryParam param) {
        Page<${entityName}DTO> page=new Page();
        ${entityName}Example example=new ${entityName}Example();
        Number total=${entityName?uncap_first}Mapper.countByExample(example);
        page.setCurrentPage(param.getPage());
        page.setPageSize(param.getLimit());
        page.setTotalCount(total.intValue());
        if(total!=null && total.intValue()<=0){
            return page;
        }
        example.setOrderByClause("id desc");

        RowBounds rowBounds=new RowBounds((param.getPage()-1)*param.getLimit(),param.getLimit());
        List<${entityName}> list=${entityName?uncap_first}Mapper.selectByExampleWithRowbounds(example,rowBounds);
        if(CollectionUtils.isEmpty(list)){
            return page;
        }
        page.setResult(list.stream().map(${entityName?uncap_first} -> {
        ${entityName}DTO dto=new ${entityName}DTO();
        BeanUtils.copyProperties(${entityName?uncap_first},dto);
            return dto;
        }).collect(Collectors.toList()));
        return page;
    }

}
