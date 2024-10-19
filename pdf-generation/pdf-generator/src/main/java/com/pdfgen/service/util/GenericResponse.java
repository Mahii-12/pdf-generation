package com.pdfgen.service.util;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GenericResponse<T> {

    int status;
    String message;
    T data;

    public GenericResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


}