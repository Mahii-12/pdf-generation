package com.pdfgen.service.PdfByIText.service;

import com.pdfgen.service.PdfByiText.service.PdfITextServiceImpl;
import com.pdfgen.service.exceptions.NotValidException;
import com.pdfgen.service.model.Item;
import com.pdfgen.service.model.PdfRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class PdfServiceTest {


    private PdfITextServiceImpl pdfService;

    @BeforeEach
    public void setUp() {
        pdfService = new PdfITextServiceImpl();
    }

    @Test
    public void testGeneratePdf_ValidRequest() throws Exception {
        PdfRequest request = new PdfRequest("Seller Name", "GSTIN", "Seller Address", "Buyer Name", "GSTIN", "Buyer Address", Collections.singletonList(new Item("Item1", "Quantity", 100.0, 100.0)));

        byte[] pdfBytes = pdfService.generatePdf(request);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }


    @Test
    public void test_generate_pdf_success() throws IOException {
        PdfITextServiceImpl pdfService = new PdfITextServiceImpl();
        PdfRequest request = new PdfRequest(
                "SellerName", "SellerGSTIN", "SellerAddress",
                "BuyerName", "BuyerGSTIN", "BuyerAddress",
                List.of(new Item("Item1", "2", 100.0, 200.0))
        );
        byte[] pdfBytes = pdfService.generatePdf(request);
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }


    @Test
    public void testGeneratePdf_BuyerEqualsSeller() {
        PdfRequest request = new PdfRequest("Same Name", "GSTIN", "Seller Address", "Same Name", "GSTIN", "Buyer Address", Collections.emptyList());

        Exception exception = assertThrows(NotValidException.class, () -> pdfService.generatePdf(request));

        assertEquals("Buyer and Seller should not be equal!", exception.getMessage());
    }

    @Test
    public void testGeneratePdf_DirectoryCreationIOException() {
        PdfRequest request = new PdfRequest("Seller Name", "GSTIN", "Seller Address", "Buyer Name", "GSTIN", "Buyer Address", Collections.emptyList());

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any())).thenThrow(new IOException("Failed to create directory"));

            Exception exception = assertThrows(IOException.class, () -> pdfService.generatePdf(request));

            assertEquals("Failed to create directory", exception.getMessage());
        }
    }


}
