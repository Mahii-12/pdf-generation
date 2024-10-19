package com.pdfgen.service.PdfByThymeleaf.service;

import com.pdfgen.service.exceptions.NotValidException;
import com.pdfgen.service.model.PdfRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PdfServiceThymleafTest {


    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PdfServiceThymleafImpl pdfService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePdf_Success() throws IOException {
        PdfRequest request = new PdfRequest();
        request.setSeller("John Doe");
        request.setBuyer("Jane Smith");
        request.setSellerAddress("123 Main St");
        request.setBuyerAddress("456 Elm St");
        request.setSellerGstin("GST123456");
        request.setBuyerGstin("GST654321");
        request.setItems(List.of());

        when(templateEngine.process(anyString(), any())).thenReturn("<html>Test PDF Content</html>");

        byte[] pdfBytes = pdfService.generatePdf(request);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }


    @Test
    public void testGeneratePdf_BuyerEqualsSeller() {
        PdfRequest request = new PdfRequest();
        request.setSeller("John Doe");
        request.setBuyer("John Doe");

        assertThrows(NotValidException.class, () -> pdfService.generatePdf(request));
    }


}