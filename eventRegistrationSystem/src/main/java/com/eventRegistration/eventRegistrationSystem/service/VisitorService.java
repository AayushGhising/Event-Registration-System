package com.eventRegistration.eventRegistrationSystem.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eventRegistration.eventRegistrationSystem.model.Visitor;
import com.eventRegistration.eventRegistrationSystem.repository.VisitorRepository;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    // @Value("${file.upload-dir}")
    // private String uploadDir;

    private final String uploadDir = "E:\\spring boot\\eventRegistrationSystem\\upload\\";

    public String registerVisitor(String fullName, String email, String phone, MultipartFile photo) throws IOException {
        // Path uploadPath = Paths.get(uploadDir);
        // if (!Files.exists(uploadPath)) {
        //     Files.createDirectories(uploadPath);
        // }
        // String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();

        // String photoPath = uploadDir + fileName;
        // Files.write(Paths.get(photoPath), photo.getBytes()); // save the file to the upload directory
        // visitor.setPhotoPath(photoPath.toString());    // set the photo path in the visitor object
        // visitorRepository.save(visitor);    // save the visitor object to the database
        
        String fileName = photo.getOriginalFilename();
        String photoPath = uploadDir + fileName;

        Visitor visitor = visitorRepository.save(Visitor.builder()
            .fullName(fullName)
            .email(email)
            .phone(phone)
            .photoPath(photoPath)
            .build());

        photo.transferTo(new File(photoPath));
        if (visitor != null) {
            return String.valueOf(visitor.getId());
            
        }
        return null;
        
    }

    public void generateBadge(Visitor visitor, OutputStream outputStream) throws IOException, DocumentException {
        // Document document = new Document(); // create a new document object
        // PdfWriter.getInstance(document, outputStream);  // create a new pdf writer object
        // document.open();

        // // Add visitor photo
        // Image photo = Image.getInstance(visitor.getPhotoPath());
        // photo.scaleToFit(100, 100);
        // document.add(photo);

        // // Add visitor name
        // document.add(new Paragraph(visitor.getFullName()));

        // // Add "Visitor" label
        // document.add(new Paragraph("Visitor"));

        // document.close();


        Document document = new Document(PageSize.A5); // Set A5 page size
        PdfWriter.getInstance(document, outputStream);
        document.open();
    
        // Create a table with one column (everything centered)
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
    
        // Visitor Photo (smaller size)
        Image photo = Image.getInstance(visitor.getPhotoPath());
        photo.scaleToFit(40, 40); // Further reduced image size
        photo.setAlignment(Element.ALIGN_CENTER);
        
        PdfPCell photoCell = new PdfPCell(photo, true);
        photoCell.setBorder(Rectangle.NO_BORDER);
        photoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        photoCell.setPaddingBottom(5);
        table.addCell(photoCell);
    
        // Visitor Name
        Font nameFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        PdfPCell nameCell = new PdfPCell(new Phrase(visitor.getFullName().toUpperCase(), nameFont));
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setBorder(Rectangle.NO_BORDER);
        nameCell.setPaddingBottom(5);
        table.addCell(nameCell);
    
        // "Visitor" Label
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
        PdfPCell visitorCell = new PdfPCell(new Phrase("Visitor", labelFont));
        visitorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        visitorCell.setBorder(Rectangle.NO_BORDER);
        visitorCell.setPaddingBottom(10);
        visitorCell.setBackgroundColor(BaseColor.RED);
        table.addCell(visitorCell);
    
        // Additional Badge Info
        Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);
        PdfPCell companyCell = new PdfPCell(new Phrase("Welcome to Our Event", infoFont));
        companyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.setPaddingBottom(5);
        table.addCell(companyCell);
    
        PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + java.time.LocalDate.now(), infoFont));
        dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setPaddingBottom(10);
        table.addCell(dateCell);
    
        // Adding a border for better styling
        PdfPCell borderCell = new PdfPCell();
        borderCell.setBorder(Rectangle.BOX);
        borderCell.setBorderWidth(1.5f);
        borderCell.setPadding(10);
        borderCell.addElement(table);
    
        PdfPTable outerTable = new PdfPTable(1);
        outerTable.setWidthPercentage(80); // Keep a balanced margin
        outerTable.addCell(borderCell);
    
        document.add(outerTable);
        document.close();
    }


}
