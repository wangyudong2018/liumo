package com.yiyun.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */
@Data
@Table(name = "sys_dict")
@NoArgsConstructor
public class DictDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //编号
    @Id
    private Long id;

    private Integer flag;

    //标签名
    private String name;
    //数据值
    private String value;
    //类型
    private String type;
    //描述
    private String description;
    //排序（升序）
    private BigDecimal sort;
    //父级编号
    private Long parentId;
    //创建者
    private Integer createBy;
    //创建时间
    private Date createDate;
    //更新者
    private Long updateBy;
    //更新时间
    private Date updateDate;
    //备注信息
    private String remarks;
    //删除标记
    private String delFlag;

    public DictDO(String name, String value) {
        this.name = name;
        this.value = value;
    }
}