package ${packageName}.web;

import com.little.g.common.ResultJson;
import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import ${packageName}.api.${entityName}Service;
import ${packageName}.dto.${entityName}DTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
* Created by ${author} on 2019/3/12.
*/
@RequestMapping("/${entityName?uncap_first}")
@RestController
public class ${entityName}Controller {
    @Resource
    private ${entityName}Service ${entityName?uncap_first}Service;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultJson add(@Valid ${entityName}DTO params){
        ResultJson r=new ResultJson();

        if(${entityName?uncap_first}Service.add(params)){
            return r;
        }
        r.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        return r;
    }

    @RequestMapping(value = "/del",method = RequestMethod.GET)
    public ResultJson del(@RequestParam Integer id){
        ResultJson r=new ResultJson();
        if(${entityName?uncap_first}Service.delete(id)){
            return r;
        }
        r.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        return r;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultJson update(@Valid ${entityName}DTO params){
        ResultJson r=new ResultJson();
        if(${entityName?uncap_first}Service.update(params)){
            return r;
        }
        r.setC(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        return r;
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResultJson list(@Valid TimeQueryParam params){
        ResultJson r=new ResultJson();
        ListResultDTO<${entityName}DTO> list= ${entityName?uncap_first}Service.list(params);
        r.setData(list);
        return r;
    }
}
