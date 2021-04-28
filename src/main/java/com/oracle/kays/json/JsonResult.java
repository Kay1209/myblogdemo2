package com.oracle.kays.json;

public class JsonResult {
    //默认成功响应
    public static JsonData success(Object data) {
        JsonData result = new JsonData();
        result.setCode(200);
        result.setMsg("ok");
        result.setData(data);
        return result;
    }

    //自定义消息的成功响应
    public static JsonData success(Object data, String msg) {
        JsonData result = new JsonData();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static JsonData success(Object data, String msg, String token) {
        JsonData result = new JsonData();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        result.setToken(token);
        return result;
    }

    //自定义消息的失败响应
    public static JsonData fail(String msg) {
        JsonData result = new JsonData();
        result.setMsg(msg);
        result.setCode(400);
        return result;
    }

    //默认失败响应
    public static JsonData fail() {
        JsonData result = new JsonData();
        result.setCode(400);
        result.setMsg("error");
        return result;
    }

    //默认未授权响应
    public static JsonData unauth() {
        JsonData result = new JsonData();
        result.setCode(401);
        result.setMsg("unauthorized");
        return result;
    }

    //默认未授权响应
    public static JsonData unauth(String msg) {
        JsonData result = new JsonData();
        result.setCode(401);
        result.setMsg(msg);
        return result;
    }
}
