package com.pdfgen.service.PdfByThymeleaf.controller;

import com.pdfgen.service.PdfByThymeleaf.service.PdfServiceThymleafImpl;
import com.pdfgen.service.model.PdfRequest;
import com.pdfgen.service.util.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/thymeleafpdf")
public class PdfThymleafController {

    private final PdfServiceThymleafImpl pdfServiceThymleaf;


    @PostMapping("/generate")
    public ResponseEntity<GenericResponse<String>> generatedf(@RequestBody PdfRequest request) {
        try {
            byte[] pdfBytes = pdfServiceThymleaf.generatePdf(request);
            String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

            GenericResponse<String> response = new GenericResponse<>(200, "PDF generated successfully.", base64Pdf);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            GenericResponse<String> errorResponse = new GenericResponse<>(500, "Error generating PDF: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
