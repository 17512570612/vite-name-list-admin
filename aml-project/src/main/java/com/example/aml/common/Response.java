package com.example.aml.common;

import lombok.Data;

@Data
public class Response {
    private int code;
    private String message;
    private String status;
    private Object result;
    private long timestamp;

    public Response() {
    }

    public Response(int code, String message, String status, Object result, long timestamp) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.result = result;
        this.timestamp = timestamp;
    }

    private static Response createResponse(int code, String message, String status, Object data, long timestamp) {
        return new Response(code, message, status, data, timestamp);
    }

    public static Response success(Object data) {
        return createResponse(200, "操作成功", "success", data, System.currentTimeMillis());
    }

    public static Response fail(int code, String message, Object data) {
        return createResponse(code, message, "fail", data, System.currentTimeMillis());
    }

    public static Response error(String message) {
        return createResponse(500, message, "error", null, System.currentTimeMillis());
    }
}
