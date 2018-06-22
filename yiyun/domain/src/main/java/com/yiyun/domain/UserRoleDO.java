package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;


@Data
public class UserRoleDO {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
}
