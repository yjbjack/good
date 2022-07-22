package com.yjb.goods.util;

public enum Status {

    OK("200", "正确"),
    REQ_REPEAT("501","请求频繁,请再5s后再次尝试！"),
    BAD_REQUEST("400", "错误的请求"),
    UNAUTHORIZED("401", "禁止访问"),
    DELETE_OK("201", "删除成功"),
    DELETE_ERROR("405", "删除失败"),
    NOT_FOUND("404", "没有可用的数据"),
    PWD_ERROR("300", "账号或者密码错误"),
    EXIT("403", "已经存在"),
    INTERNAL_SERVER_ERROR("500", "服务器遇到了一个未曾预料的状况"),
    SERVICE_UNAVAILABLE("503", "服务器当前无法处理请求"),
    ERROR("9999", "数据不能为空");


    private String code;
    private String reason;


    Status(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }
    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code='" + code + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }






}
