package com.example.KRiT_2025_backend.Pdf;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
public class FileController {

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> servePdfWithFilename(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home"), "uploads", filename);
            Resource resource = new FileSystemResource(filePath);

            String title = PdfController.fileNameToTitleMap.getOrDefault(filename, filename);
            String safeTitle = title.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String customFilename = safeTitle + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + customFilename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

