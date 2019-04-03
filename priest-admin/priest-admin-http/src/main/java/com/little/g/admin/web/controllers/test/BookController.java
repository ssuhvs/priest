package com.little.g.admin.web.controllers.test;

import com.little.g.admin.common.annotation.ModuleManage;
import com.little.g.admin.common.annotation.ModuleOperation;
import com.little.g.admin.common.enums.ModuleType;
import com.little.g.admin.common.enums.OperationType;
import com.little.g.admin.common.page.Page;
import com.little.g.admin.dto.BookDTO;
import com.little.g.admin.service.BookService;
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
@RequestMapping(value = "/book")
@ModuleManage(ModuleType.ROLE)
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Resource
    private BookService bookService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@Valid PageQueryParam param,
                       Model view) {

        Page<BookDTO> page = bookService.pageList(param);

        //放入page对象。
        view.addAttribute("page", page);

        return "/jsp/book/book-list";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.GET})
    public String edit(Integer id, Model view) {


        if (id != null && id > 0) {
            BookDTO book = bookService.get(id);
            view.addAttribute("book", book);
        }

        return "/jsp/book/book-edit";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.ADD, description = "添加/修改书本")
    public String save(BookDTO book,
                       Model view) {
        if (book != null) {
            boolean r;
            if (book.getId() != null && book.getId() > 0) {
                r = bookService.update(book);
            } else {
                r = bookService.add(book);
            }
            if (r) {
                return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存成功！");
            }
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "保存失败！");
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE,RequestMethod.POST})
    @ResponseBody
    @ModuleOperation(value = OperationType.DELETE, description = "删除角色")
    public String delete(@RequestParam Integer id,
                         Model view) {
        if (bookService.delete(id)) {
            return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除成功！");
        }
        return String.format(AdminConstants.WEB_IFRAME_SCRIPT, "删除失败！");
    }
}
