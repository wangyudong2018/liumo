package com.yiyun.app.system.resp;

import lombok.Data;

import java.io.Serializable;
@Data
public class HelpDoc implements Serializable{
    //问题
    private String question;
    //回答
    private String answer;


}
