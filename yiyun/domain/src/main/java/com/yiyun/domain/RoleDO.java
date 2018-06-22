package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

@Data
public class RoleDO {
    @Id
    private Long roleId;
    private String roleName;
    private String roleSign;
    private String remark;
    private Long userIdCreate;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;

    @Transient
    private List<Long> menuIds;
}
