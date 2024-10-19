package com.pdfgen.service.PdfByThymeleaf.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.pdfgen.service.exceptions.BuyerMissingException;
import com.pdfgen.service.exceptions.ErrorCodes;
import com.pdfgen.service.exceptions.NotValidException;
import com.pdfgen.service.exceptions.SellerMissingException;
import com.pdfgen.service.model.PdfRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Service
@AllArgsConstructor
public class PdfServiceThymleafImpl implements PdfServiceThymeleaf {

    private TemplateEngine templateEngine;


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
            throw new SellerMissingException(ErrorCodes.SELLER_IS_MISSING, "Seller is Missing!");
        }

        if (request.getBuyer() == null || request.getBuyer().isEmpty()) {
            throw new BuyerMissingException(ErrorCodes.BUYER_IS_MISSING, "Buyer is missing!");
        }

        if (request.getBuyer().equals(request.getSeller())) {
            log.error("Buyer and Seller are the same: {}", request.getBuyer());
            throw new NotValidException(ErrorCodes.NOT_VALID, "Buyer and Seller should not be equal!");
        }

        Context context = new Context();
        String sellerDetails = String.format("Seller:<br>%s<br>%s<br>GSTIN: %s",
                request.getSeller(),
                request.getSellerAddress(),
                request.getSellerGstin()
        );

        String buyerDetails = String.format("Buyer:<br>%s<br>%s<br>GSTIN: %s",
                request.getBuyer(),
                request.getBuyerAddress(),
                request.getBuyerGstin()
        );

        context.setVariable("sellerDetails", sellerDetails);
        context.setVariable("buyerDetails", buyerDetails);
        context.setVariable("items", request.getItems());

        String htmlContent = templateEngine.process("invoice", context);

        try (FileOutputStream fos = new FileOutputStream(pdfPath.toFile())) {
            HtmlConverter.convertToPdf(htmlContent, fos);
            log.info("PDF generated successfully at: {}", pdfPath.toString());
        } catch (Exception e) {
            log.error("Error during PDF generation: {}", e.getMessage());
            throw new IOException("Error generating PDF: " + e.getMessage(), e);
        }

        try {
            return Files.readAllBytes(pdfPath);
        } catch (IOException e) {
            log.error("Failed to read the generated PDF file: {}", e.getMessage());
            throw new IOException("Error reading the generated PDF file: " + e.getMessage(), e);
        }
    }

}
