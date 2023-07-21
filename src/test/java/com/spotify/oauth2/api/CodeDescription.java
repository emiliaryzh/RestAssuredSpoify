package com.spotify.oauth2.api;

import org.apache.http.HttpStatus;

public enum CodeDescription {
    CODE_200(HttpStatus.SC_OK, ""),
    CODE_201(HttpStatus.SC_CREATED, ""),
    CODE_400(HttpStatus.SC_BAD_REQUEST, "Missing required field: name"),
    CODE_401(HttpStatus.SC_UNAUTHORIZED, "Invalid access token");

    private final int code;
    private final String message;

    CodeDescription(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
