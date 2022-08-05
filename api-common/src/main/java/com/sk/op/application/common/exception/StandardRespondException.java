package com.sk.op.application.common.exception;

import com.sk.op.application.common.dto.ResponseStatus;

public class StandardRespondException extends RuntimeException implements RespondException {

    private final int status;

    public StandardRespondException() {
        super(ResponseStatus.UNKNOWN.getMessage());
        this.status = ResponseStatus.UNKNOWN.getStatus();
    }

    public StandardRespondException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.status = responseStatus.getStatus();
    }

    public StandardRespondException(ResponseStatus responseStatus, String message) {
        super(message);
        this.status = responseStatus.getStatus();
    }

    public StandardRespondException(String message) {
        super(message);
        this.status = ResponseStatus.FAILED.getStatus();
    }

    public StandardRespondException(int status, String message) {
        super(message);
        this.status = status;
    }

    public StandardRespondException(Throwable cause) {
        RespondException respondException = RespondException.extractRespondException(cause);
        if (respondException != null) {
            this.status = respondException.getStatus();
        } else {
            this.status = ResponseStatus.UNKNOWN.getStatus();
        }
    }

    public StandardRespondException(String message, Throwable cause) {
        super(message);
        RespondException respondException = RespondException.extractRespondException(cause);
        if (respondException != null) {
            this.status = respondException.getStatus();
        } else {
            this.status = ResponseStatus.FAILED.getStatus();
        }
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
