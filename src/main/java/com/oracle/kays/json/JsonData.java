package com.oracle.kays.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//用来封装数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonData implements Serializable,Cloneable {

    private Object data;
    private int code;
    private String msg;
    private String token; //令牌，用于会话
}
