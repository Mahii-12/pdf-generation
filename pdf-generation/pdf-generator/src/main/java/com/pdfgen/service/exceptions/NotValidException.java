package com.pdfgen.service.exceptions;

import lombok.Getter;

@Getter
public class NotValidException extends RuntimeException {
    private final ErrorCodes errorCodes;

    public NotValidException(ErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}