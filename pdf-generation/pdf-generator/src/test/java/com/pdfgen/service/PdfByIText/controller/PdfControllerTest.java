package com.pdfgen.service.PdfByIText.controller;

import com.pdfgen.service.PdfByiText.controller.PdfITextController;
import com.pdfgen.service.PdfByiText.service.PdfITextServiceImpl;
import com.pdfgen.service.model.Item;
import com.pdfgen.service.model.PdfRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

class PdfControllerTest {

    @InjectMocks
    private PdfITextController pdfController;

    @Mock
    private PdfITextServiceImpl pdfService;

    private PdfRequest pdfRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfRequest = new PdfRequest();
        pdfRequest.setSeller("Sirma");
        pdfRequest.setBuyer("Vedant Computers");
    }


    @Test
    void test_generate_pdf_success() throws IOException {
        PdfITextServiceImpl pdfServiceMock = Mockito.mock(PdfITextServiceImpl.class);
        PdfITextController controller = new PdfITextController(pdfServiceMock);
        PdfRequest request = new PdfRequest("Seller", "GSTIN123", "Seller Address", "Buyer", "GSTIN456", "Buyer Address", List.of(new Item("Item1", "1", 100.0, 100.0)));

        byte[] expectedPdfContent = new byte[]{1, 2, 3};
        Mockito.when(pdfServiceMock.generatePdf(request)).thenReturn(expectedPdfContent);

        ResponseEntity<byte[]> response = controller.generatePdf(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertArrayEquals(expectedPdfContent, response.getBody());
        Assertions.assertTrue(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0).contains("Seller_Buyer_invoice.pdf"));
    }
}
