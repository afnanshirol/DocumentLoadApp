package com.alphasense.document.load.dto;

import com.alphasense.document.load.constants.ErrorCode;

import java.util.Objects;

public class Error {

    private ErrorCode errorCode;
    private String details;
    private String description;
    private boolean recoverable;
    private String source;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecoverable() {
        return recoverable;
    }

    public void setRecoverable(boolean recoverable) {
        this.recoverable = recoverable;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return recoverable == error.recoverable && errorCode.equals(error.errorCode) && Objects.equals(details, error.details) && Objects.equals(description, error.description) && source.equals(error.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, details, description, recoverable, source);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Error{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", recoverable=").append(recoverable);
        sb.append(", source='").append(source).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Error(ErrorCode errorCode, String details, String description, boolean recoverable, String source) {
        this.errorCode = errorCode;
        this.details = details;
        this.description = description;
        this.recoverable = recoverable;
        this.source = source;
    }
}
