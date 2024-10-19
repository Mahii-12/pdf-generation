package com.pdfgen.service.PdfByThymeleaf.controller;

import com.pdfgen.service.PdfByThymeleaf.service.PdfServiceThymleafImpl;
import com.pdfgen.service.model.PdfRequest;
import com.pdfgen.service.util.GenericResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;

class PdfThymleafControllerTest {


    @InjectMocks
    private PdfThymleafController pdfController;

    @Mock
    private PdfServiceThymleafImpl pdfServiceThymleaf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_generate_pdf_success() throws IOException {
        PdfServiceThymleafImpl pdfServiceMock = Mockito.mock(PdfServiceThymleafImpl.class);
        PdfThymleafController controller = new PdfThymleafController(pdfServiceMock);
        PdfRequest request = new PdfRequest("Seller", "GSTIN123", "Seller Address", "Buyer", "GSTIN456", "Buyer Address", new ArrayList<>());

        byte[] pdfBytes = new byte[]{1, 2, 3};
        Mockito.when(pdfServiceMock.generatePdf(request)).thenReturn(pdfBytes);

        ResponseEntity<GenericResponse<String>> response = controller.generatedf(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("PDF generated successfully.", response.getBody().getMessage());
        Assertions.assertNotNull(response.getBody().getData());
    }


    @Test
    public void test_generate_pdf_ioexception() throws IOException {
        PdfServiceThymleafImpl pdfServiceMock = Mockito.mock(PdfServiceThymleafImpl.class);
        PdfThymleafController controller = new PdfThymleafController(pdfServiceMock);
        PdfRequest request = new PdfRequest("Seller", "GSTIN123", "Seller Address", "Buyer", "GSTIN456", "Buyer Address", new ArrayList<>());

        Mockito.when(pdfServiceMock.generatePdf(request)).thenThrow(new IOException("IO Error"));

        ResponseEntity<GenericResponse<String>> response = controller.generatedf(request);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Error generating PDF: IO Error", response.getBody().getMessage());
        Assertions.assertNull(response.getBody().getData());
    }

}