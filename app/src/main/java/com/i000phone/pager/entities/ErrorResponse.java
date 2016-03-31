package com.i000phone.pager.entities;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ErrorResponse extends Response {
    private final String error;
    public ErrorResponse(String json) {
        super(json);
        error = json;
    }

    public String getError() {
        return error;
    }
}
