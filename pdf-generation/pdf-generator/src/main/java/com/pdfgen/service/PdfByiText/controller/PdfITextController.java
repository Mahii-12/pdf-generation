package com.pdfgen.service.PdfByiText.controller;

import com.pdfgen.service.PdfByiText.service.PdfITextServiceImpl;
import com.pdfgen.service.model.PdfRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/api/itextpdf")
public class PdfITextController {

    private final PdfITextServiceImpl pdfService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody PdfRequest request) {
        log.info("Received request to generate PDF for Seller: {}, Buyer: {}", request.getSeller(), request.getBuyer());

        try {
            byte[] pdfContent = pdfService.generatePdf(request);

            log.info("PDF processed successfully for Seller: {}, Buyer: {}", request.getSeller(), request.getBuyer());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + request.getSeller().replaceAll(" ", "_") + "_" + request.getBuyer().replaceAll(" ", "_") + "_invoice.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);

        } catch (IOException e) {
            log.error("Error generating PDF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }


}
