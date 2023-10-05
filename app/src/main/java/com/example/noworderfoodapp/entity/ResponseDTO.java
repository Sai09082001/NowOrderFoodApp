package com.example.noworderfoodapp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseDTO<T> {
    private int status; // 200, 400, 500
    private String msg;
    private T data;

    @JsonCreator
    public ResponseDTO(@JsonProperty("status")int status,@JsonProperty("msg") String msg,@JsonProperty("data") T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}