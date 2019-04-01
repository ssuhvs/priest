package com.little.g.admin.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 模块分类
 * */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ModuleType implements EnumBase<String>{
	MENU("menu", "资源管理"),
	ROLE("role", "角色管理"),
	WITHDRAW("withdraw", "提现管理"),
	ADMIN_USER("admin_user", "后台用户管理"),
	HEAVYOPERATION("heavy_operation", "重型操作"),
	MEMBER("member", "用户管理");

	private String code;
	private String message;
	
	ModuleType(String code, String message){
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public static ModuleType getModuleType(String code){
		for (ModuleType ul : values()) {
            if (ul.getCode().equals(code)) {
                return ul;
            }
        }
        return null;
	}

}
