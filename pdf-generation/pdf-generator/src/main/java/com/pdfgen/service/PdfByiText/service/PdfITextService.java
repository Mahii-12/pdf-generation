package com.pdfgen.service.PdfByiText.service;

import com.pdfgen.service.model.PdfRequest;

import java.io.IOException;

public interface PdfITextService {
    byte[] generatePdf(PdfRequest request) throws IOException;
}
