package com.example.KRiT_2025_backend.Pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractPdfData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nieprawidłowy plik PDF"));
        }

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            Map<String, String> result = new HashMap<>();
            result.put("abstract", extractAbstract(text));
            result.put("keywords", extractKeywords(text));
            result.put("title", extractTitle(text));
            result.put("authors", extractAuthors(text));

            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Błąd podczas przetwarzania PDF"));
        }
    }

    private String extractAbstract(String text) {
        return extractBetween(text, "Streszczenie", "Słowa kluczowe");
    }

    private String extractKeywords(String text) {
        return extractAfter(text, "Słowa kluczowe");
    }

    private String extractTitle(String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().toUpperCase().equals(line.trim()) && line.trim().length() > 10) {
                return line.trim();
            }
        }
        return "";
    }

    private String extractAuthors(String text) {
        String[] lines = text.split("\n");
        for (int i = 0; i < Math.min(lines.length, 20); i++) {
            if (lines[i].toLowerCase().contains("krit 2025") && i + 1 < lines.length) {
                return lines[i + 1].trim();
            }
        }
        return "";
    }

    private String extractBetween(String text, String start, String end) {
        int startIdx = text.toLowerCase().indexOf(start.toLowerCase());
        int endIdx = text.toLowerCase().indexOf(end.toLowerCase());
        if (startIdx >= 0 && endIdx > startIdx) {
            return text.substring(startIdx + start.length(), endIdx).trim();
        }
        return "";
    }

    private String extractAfter(String text, String marker) {
        int index = text.toLowerCase().indexOf(marker.toLowerCase());
        if (index >= 0) {
            String rest = text.substring(index + marker.length());
            return rest.split("\n")[0].trim();
        }
        return "";
    }
}

