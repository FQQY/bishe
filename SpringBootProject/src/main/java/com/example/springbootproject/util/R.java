package com.example.springbootproject.util;


import java.io.Serializable;

/**
 * @author fqgxing
 * @create 2023-02-27-17:22
 */

public class R<T> implements Serializable {

    private Object message;

    private T data;

    public R() {
    }

    public R(Object message, T data) {
        this.message = message;
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "message=" + message +
                ", data=" + data +
                '}';
    }
}
