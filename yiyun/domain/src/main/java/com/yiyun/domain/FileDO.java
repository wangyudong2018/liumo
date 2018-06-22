package com.yiyun.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-19 16:02:20
 */
@Data
@NoArgsConstructor
public class FileDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private Long id;
    // 文件类型
    private Integer type;
    // URL地址
    private String url;
    // 创建时间
    private Date createDate;

    public FileDO(Integer type, String url, Date createDate) {
        super();
        this.type = type;
        this.url = url;
        this.createDate = createDate;
    }
}
