package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
@Data
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //
    @Id
    private Long userId;
    // 用户名
    private String username;
    // 用户真实姓名
    private String name;
    // 密码
    private String password;
    // 部门
    private Long deptId;
    private String deptName;
    // 邮箱
    private String email;
    // 手机号
    private String mobile;
    //用户所属区域 , 仅可查看/操作所属区域的
    private String city;
    // 状态 0:禁用，1:正常
    private Integer status;
    // 创建用户id
    private Long userIdCreate;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    //邀请码 内部人士开member用此邀请码
    private String investCode;
    //手机号 商务有

    //角色
    @Transient
    private List<Long> roleIds;
}
