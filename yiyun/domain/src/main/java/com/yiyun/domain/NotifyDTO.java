package com.yiyun.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyDTO extends NotifyDO {

    private static final long serialVersionUID = 1L;

    private String isRead;

    private String before;

    private String sender;
}
