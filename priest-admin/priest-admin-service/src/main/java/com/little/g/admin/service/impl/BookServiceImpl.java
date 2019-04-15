package com.little.g.admin.service.impl;

import com.little.g.common.dto.Page;
import com.little.g.common.params.PageQueryParam;
import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.admin.service.BookService;
import com.little.g.admin.dto.BookDTO;
import com.little.g.admin.mapper.BookMapper;
import com.little.g.admin.model.Book;
import com.little.g.admin.model.BookExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;

/**
* Created by lengligang on 2019/3/9.
*/
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Override
    public boolean add(BookDTO entity) {
        Book book=new Book();
        BeanUtils.copyProperties(entity,book);
        return bookMapper.insertSelective(book)>0;
    }

    @Override
    public BookDTO get(Integer id) {
        Book book=bookMapper.selectByPrimaryKey(id);
        if(book == null){
            return null;
        }
        BookDTO dto=new BookDTO();
        BeanUtils.copyProperties(book,dto);
        return dto;
    }

    @Override
    public boolean update(BookDTO entity) {
        if(entity.getId() == null) return false;
        Book book=new Book();
        BeanUtils.copyProperties(entity,book);
        return bookMapper.updateByPrimaryKeySelective(book)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return bookMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public ListResultDTO<BookDTO> list(TimeQueryParam param) {
        ListResultDTO<BookDTO> result=param.getResult(ListResultDTO.class);

            BookExample example = new BookExample();
            example.or().andCreateTimeLessThan(param.getLast());
            example.setOrderByClause(String.format("create_time desc limit %d",result.getLimit()));
            List<Book> list= bookMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)){
                return result;
            }
            result.setLast(list.get(list.size()-1).getCreateTime());
            result.setList(list.stream().map(entity->{
            BookDTO dto=new BookDTO();
            BeanUtils.copyProperties(entity,dto);
            return dto;
            }).collect(Collectors.toList()));

            return result;
    }

    @Override
    public Page<BookDTO> pageList(@NotBlank PageQueryParam param) {
        Page<BookDTO> page=new Page();
        BookExample example=new BookExample();
        Number total=bookMapper.countByExample(example);
        page.setCurrentPage(param.getPage());
        page.setPageSize(param.getLimit());
        page.setTotalCount(total.intValue());
        if(total!=null && total.intValue()<=0){
            return page;
        }
        example.setOrderByClause("id desc");

        RowBounds rowBounds=new RowBounds((param.getPage()-1)*param.getLimit(),param.getLimit());
        List<Book> list=bookMapper.selectByExampleWithRowbounds(example,rowBounds);
        if(CollectionUtils.isEmpty(list)){
            return page;
        }
        page.setResult(list.stream().map(book -> {
        BookDTO dto=new BookDTO();
        BeanUtils.copyProperties(book,dto);
            return dto;
        }).collect(Collectors.toList()));
        return page;
    }

}
