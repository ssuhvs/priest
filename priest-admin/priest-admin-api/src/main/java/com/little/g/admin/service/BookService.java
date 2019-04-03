package com.little.g.admin.service;

import com.little.g.admin.common.page.Page;
import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.PageQueryParam;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.admin.dto.BookDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
* Created by lengligang on 2019/3/9.
*/
public interface BookService {
/**
* 添加
* @param entity
* @return
*/
boolean add(@Valid BookDTO entity);

/**
* 根据id获取
* @param id
* @return
*/
BookDTO get(@NotBlank Integer id);

/**
* 更新
* @param entity
* @return
*/
boolean update(@Valid BookDTO entity);

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
ListResultDTO<BookDTO> list(@NotBlank TimeQueryParam param);

/**
 * 分页查询逻辑
 * @param param
 * @return
 */
Page<BookDTO> pageList(@NotBlank PageQueryParam param);

}
