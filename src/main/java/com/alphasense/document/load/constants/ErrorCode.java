package com.alphasense.document.load.constants;

public enum ErrorCode {

    SERVER_ERROR ("500001"),
    BAD_REQUEST ("400001");

    private final String value;

    private ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
