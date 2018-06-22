package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;

@Data
public class RoleMenuDO {
	@Id
	private Long id;
	private Long  roleId;
	private Long menuId;
}
