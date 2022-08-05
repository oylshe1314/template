package com.sk.op.application.common.exception;

import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.common.dto.ResponseStatus;

public interface RespondException {

    int getStatus();

    String getMessage();

    default ResponseDto<?> response() {
        return this.getStatus() == ResponseStatus.FAILED.getStatus() ? ResponseDto.fail(this.getMessage()) : ResponseDto.fail(getStatus(), getMessage());
    }

    static RespondException extractRespondException(Throwable cause) {
        while (cause != null) {
            if (cause instanceof RespondException) {
                return (RespondException) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }
}
