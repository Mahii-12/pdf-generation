package com.pdfgen.service.PdfByThymeleaf.service;

import com.pdfgen.service.model.PdfRequest;

import java.io.IOException;

public interface PdfServiceThymeleaf {
    public byte[] generatePdf(PdfRequest request) throws IOException;
}
