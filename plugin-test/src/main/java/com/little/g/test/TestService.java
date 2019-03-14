package com.little.g.test;
import com.little.g.dto.classNameDTO;
import com.bat.common.page.Page;
import java.util.List;
 
/**
 * 这是测试业务层访问接口
 * @author  lengligang
 * @version 哈哈Service.java, v 0.1  2019-3-14 16:03:02
 */
public interface classNameService
{
	     Long saveclassName(classNameDTO 哈哈DTO);

         Long deleteclassName(Long id); sxxxx

         classNameDTO queryclassNameById(Long id);

         List<classNameDTO> queryclassName(classNameDTO 哈哈DTO);

         Page<classNameDTO> queryclassNamePage(classNameDTO 哈哈DTO);
}