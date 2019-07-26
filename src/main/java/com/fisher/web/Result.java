package com.fisher.web;


import com.fisher.utils.JsonUtils;

/**
 * 接口返回结果集对象
 * code:  为 0 表示操作成功, 1 或 2 等正数表示用户输入错误, -1, -2 等负数表示系统错误
 * msg:   消息
 * data:  返回结果数据
 */
public class Result {

    private int code;
    private String msg;
    private String data;

    private Result(){}

    public static Result error(int code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    public static Result success(String data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(JsonUtils.toJson(object));
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
