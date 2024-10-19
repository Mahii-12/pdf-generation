package com.pdfgen.service.PdfByiText.service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.pdfgen.service.exceptions.BuyerMissingException;
import com.pdfgen.service.exceptions.ErrorCodes;
import com.pdfgen.service.exceptions.NotValidException;
import com.pdfgen.service.exceptions.SellerMissingException;
import com.pdfgen.service.model.Item;
import com.pdfgen.service.model.PdfRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Service
@AllArgsConstructor
public class PdfITextServiceImpl implements PdfITextService {


    @Override
    public byte[] generatePdf(PdfRequest request) throws IOException {
        String userHome = System.getProperty("user.home");
        Path invoiceDir = Paths.get(userHome, "Downloads", "Invoice_");

        try {
            Files.createDirectories(invoiceDir);
            log.info("Invoice directory created at: {}", invoiceDir.toString());
        } catch (IOException e) {
            log.error("Failed to create invoice directory: {}", e.getMessage());
            throw e;
        }

        String fileName = request.getSeller().replaceAll(" ", "_") + "_" + request.getBuyer().replaceAll(" ", "_") + "_invoice.pdf";
        Path pdfPath = invoiceDir.resolve(fileName);

        if (request.getSeller() == null || request.getSeller().isEmpty()) {
            log.error("Seller information is missing in the request.");
            throw new SellerMissingException(ErrorCodes.SELLER_IS_MISSING, "Seller is Missing!");
        }

        if (request.getBuyer() == null || request.getBuyer().isEmpty()) {
            log.error("Buyer information is missing in the request.");
            throw new BuyerMissingException(ErrorCodes.BUYER_IS_MISSING, "Buyer is Missing!");
        }


        if (request.getBuyer().equals(request.getSeller())) {
            log.error("Buyer and Seller are the same: {}", request.getBuyer());
            throw new NotValidException(ErrorCodes.NOT_VALID, "Buyer and Seller should not be equal!");
        }

        try (PdfWriter writer = new PdfWriter(pdfPath.toString());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            log.info("Generating PDF: {}", pdfPath.toString());

            Table combinedTable = new Table(4);

            String sellerDetails = String.format("Seller: \n%s\n%s\nGSTIN: %s",
                    request.getSeller(), request.getSellerAddress(), request.getSellerGstin());
            Cell sellerCell = new Cell(1, 2).add(new Paragraph(sellerDetails)).setPadding(10);
            combinedTable.addCell(sellerCell);

            String buyerDetails = String.format("Buyer: \n%s\n%s\nGSTIN: %s",
                    request.getBuyer(), request.getBuyerAddress(), request.getBuyerGstin());
            Cell buyerCell = new Cell(1, 2).add(new Paragraph(buyerDetails)).setPadding(10);
            combinedTable.addCell(buyerCell);

            combinedTable.addCell(new Cell().add(new Paragraph("Item Name")).setTextAlignment(TextAlignment.CENTER).setPadding(10));
            combinedTable.addCell(new Cell().add(new Paragraph("Quantity")).setTextAlignment(TextAlignment.CENTER).setPadding(10));
            combinedTable.addCell(new Cell().add(new Paragraph("Rate")).setTextAlignment(TextAlignment.CENTER).setPadding(10));
            combinedTable.addCell(new Cell().add(new Paragraph("Amount")).setTextAlignment(TextAlignment.CENTER).setPadding(10));

            for (Item item : request.getItems()) {
                combinedTable.addCell(new Cell().add(new Paragraph(item.getName())).setTextAlignment(TextAlignment.CENTER).setPadding(10));
                combinedTable.addCell(new Cell().add(new Paragraph(item.getQuantity())).setTextAlignment(TextAlignment.CENTER).setPadding(10));
                combinedTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))).setTextAlignment(TextAlignment.CENTER).setPadding(10));
                combinedTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))).setTextAlignment(TextAlignment.CENTER).setPadding(10));
            }

            Cell emptyCell = new Cell(1, 4).setHeight(60);
            combinedTable.addCell(emptyCell);

            document.add(combinedTable);
            log.info("PDF generation completed successfully for: {}", pdfPath.toString());

        } catch (Exception e) {
            log.error("Error during PDF generation: {}", e.getMessage());
            throw new IOException("Error generating PDF: " + e.getMessage(), e);
        }

        return Files.readAllBytes(pdfPath);
    }

}
