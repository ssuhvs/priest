package ${servicePackage};
import ${topLevel}.dto.${className}DTO;
import com.bat.common.page.Page;
import java.util.List;
 
/**
 * ${tablecomment}业务层访问接口
 * @author  ${author}
 * @version ${classObjectName}Service.java, v 0.1  ${.now?datetime}
 */
public interface ${className}Service
{
	     Long save${className}(${className}DTO ${classObjectName}DTO);

         Long delete${className}(Long id); sxxxx

         ${className}DTO query${className}ById(Long id);

         List<${className}DTO> query${className}(${className}DTO ${classObjectName}DTO);

         Page<${className}DTO> query${className}Page(${className}DTO ${classObjectName}DTO);
}