package com.pdfgen.service.exceptions;

import lombok.Getter;

@Getter
public class SellerMissingException extends RuntimeException {

    private final ErrorCodes errorCodes;

    public SellerMissingException(ErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
