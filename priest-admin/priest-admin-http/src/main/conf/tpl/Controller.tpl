package ${packageName};

import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.admin.common.page.Page;
import ${basePackage}.dto.BookDTO;
import ${basePackage}.service.${entityName}Service;
import com.little.g.admin.web.common.AdminConstants;
import com.little.g.common.params.PageQueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "${uri}")
@ModuleManage(ModuleType.${module})
public class ${entityName}Controller {
    private static final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);

    @Resource
    private ${entityName}Service ${entityName?uncap_first}Service;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@Valid PageQueryParam param,
                       Model view) {

        Page<${entityName}DTO> page = bookService.pageList(param);

        //放入page对象。
        view.addAttribute("page", page);

        return "/jsp/${entityName?uncap_first}/${entityName?uncap_first}-list";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.GET})
    public String edit(Integer id, Model view) {


        if (id != null && id > 0) {
            ${entityName}DTO ${entityName?uncap_first} = ${entityName?uncap_first}Service.get(id);
            view.addAttribute("${entityName?uncap_first}", ${entityName?uncap_first});
        }

        return "/jsp/${entityName?uncap_first}/${entityName?uncap_first}-edit";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.ADD, description = "添加/修改书本")
    public String save(${entityName}DTO ${entityName?uncap_first},
                       Model view) {
        if (${entityName?uncap_first} != null) {
            boolean r;
            if (${entityName?uncap_first}.getId() != null && ${entityName?uncap_first}.getId() > 0) {
                r = ${entityName?uncap_first}Service.update(${entityName?uncap_first});
            } else {
                r = ${entityName?uncap_first}Service.add(${entityName?uncap_first});
            }
            if (r) {
                return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存成功！");
            }
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存失败！");
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.DELETE, description = "删除角色")
    public String delete(@RequestParam Integer id,Model view) {
        if (${entityName?uncap_first}Service.delete(id)) {
            return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除成功！");
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除失败！");
    }
}
