package com.example.document_management_tool.service;

import com.example.document_management_tool.entity.Documents;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private Logger logger = LoggerFactory.getLogger(PdfService.class);


    private final DocumentService documentService;

    public PdfService(DocumentService documentService) {
        this.documentService = documentService;
    }


    public ByteArrayInputStream createPdf(Long id) {
        logger.info("Create PDF Started: ");

        String title = "Welcome To BEM-GROUP";
        String content = "Building consultant in Toronto, Australia";

        Documents documents = documentService.getDocumentById(id);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();


        PdfWriter.getInstance(document, out);

        HeaderFooter footer = new HeaderFooter(false, new Phrase(" Paul Tomal"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        document.setFooter(footer);

        document.open();

        try {

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            document.add(titlePara);

            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Paragraph paragraph = new Paragraph(content, paraFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            if (documents != null) {
                Paragraph dataPara = new Paragraph();
                dataPara.setAlignment(Element.ALIGN_CENTER);
                dataPara.setFont(dataFont);
                dataPara.add("\n User: " + documents.getUserInfo().getUsername() + "\n");
                dataPara.add("Subject: " + documents.getSubject() + "\n");
                dataPara.add("Message: " + documents.getMessage() + "\n");
                document.add(dataPara);
            }


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        document.close();


        return new ByteArrayInputStream(out.toByteArray());

    }
}
