package com.pdfgen.service.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    SELLER_IS_MISSING(1),
    BUYER_IS_MISSING(2),
    NOT_VALID(3);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

}
