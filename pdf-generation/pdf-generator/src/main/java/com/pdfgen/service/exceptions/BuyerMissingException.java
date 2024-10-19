package com.pdfgen.service.exceptions;

import lombok.Getter;

@Getter
public class BuyerMissingException extends RuntimeException {
    private final ErrorCodes errorCodes;

    public BuyerMissingException(ErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
