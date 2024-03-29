package com.example.document_management_tool.controller;

import com.example.document_management_tool.service.PdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/createPdf/{id}")
    public ResponseEntity<?> createPdf(@PathVariable Long id){
        ByteArrayInputStream pdf = pdfService.createPdf(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition","inline;file=paul.pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
