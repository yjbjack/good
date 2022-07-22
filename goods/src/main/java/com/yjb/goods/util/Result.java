package com.yjb.goods.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    //状态码
    private String status;

    //状态信息
    private String message;

    //数据
    private T data;

    private Result(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(String message) {
        this.message = message;
    }

    /**
     * 创建一个带有状态、消息和数据的结果对象.
     *
     * @param status
     *            状态
     * @param message
     *            消息内容
     * @param data
     *            数据
     * @return 结构数据
     */

    public static  <T> Result<T> bulidResult(Status status,String message,T data){
        return new Result<T>(status.getCode(),status.getReason(),data);
    }

    public static  <T> Result<T> bulidResult(Status status,String message){
        return new Result<T>(status.getCode(),message);
    }
    public static  <T> Result<T> bulidResult(Status status,T data){
        return new Result<T>(status.getCode(),status.getReason(),data);
    }

    public static  <T> Result<T> bulidResult(Status status){
        return new Result<T>(status.getCode(),status.getReason());
    }


}
