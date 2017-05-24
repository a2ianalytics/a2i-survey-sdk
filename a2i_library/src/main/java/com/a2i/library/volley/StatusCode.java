package com.a2i.library.volley;

import java.io.Serializable;

public class StatusCode implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
