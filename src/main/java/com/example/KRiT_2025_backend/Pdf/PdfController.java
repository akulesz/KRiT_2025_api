package com.example.KRiT_2025_backend.Pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    public static final Map<String, String> fileNameToTitleMap = new HashMap<>();

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractPdfData(@RequestParam("file") MultipartFile file) {
        if (!isValidPdf(file)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nieprawidłowy plik PDF"));
        }

        try {
            File savedFile = saveUploadedFile(file);
            Map<String, String> result = processPdfFile(savedFile, file.getOriginalFilename());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Błąd podczas przetwarzania PDF"));
        }
    }

    private boolean isValidPdf(MultipartFile file) {
        return file != null && !file.isEmpty()
                && Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".pdf");
    }

    private File saveUploadedFile(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.home") + "/uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        return filePath.toFile();
    }

    private Map<String, String> processPdfFile(File pdfFile, String originalFileName) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String rawText = stripper.getText(document);
            String text = cleanText(rawText);
            String textForTitle = cleanTextBesidesEnter(rawText);

            Map<String, String> result = new HashMap<>();
            String extractedTitle = extractTitle(textForTitle);
            result.put("abstract", extractAbstract(text));
            result.put("keywords", extractKeywords(text));
            result.put("title", extractedTitle);
            result.put("authors", extractAuthors(text));

            setPdfMetadata(document, extractedTitle);
            document.save(pdfFile);

            String pdfUrl = generatePdfUrl(pdfFile.getName());
            result.put("pdfUrl", pdfUrl);

            fileNameToTitleMap.put(pdfFile.getName(), extractedTitle);

            return result;
        }
    }

    private void setPdfMetadata(PDDocument document, String title) {
        PDDocumentInformation info = new PDDocumentInformation();
        info.setTitle(title);
        info.setAuthor("KRiT 2025");
        document.setDocumentInformation(info);
    }

    private String generatePdfUrl(String fileName) {
        String baseUrl2 = "http://172.20.10.4:8080";
        String baseUrl1 = "http://10.0.2.2:8080";
        String baseUrl = "http://localhost:8080";
        return baseUrl + "/uploads/" + fileName;
    }


    private String cleanText(String text) {
        String cleanedText = text.replaceAll("[ \\t\\r\\f\\v]+", " ").trim();
        cleanedText = cleanedText.replaceAll("[^\\x20-\\x7EżźćńółęąśŁŻŹĆŃÓŁŚĄĘ\\n]+", "");
        cleanedText = cleanedText.replaceAll("(?<! )-\\s+", "");
        return cleanedText;
    }

    private String cleanTextBesidesEnter(String text) {
        String cleaned = text.replaceAll("[ \\t\\x0B\\f\\r]+", " ");
        cleaned = cleaned.replaceAll("(?m)^\\s+", "");
        cleaned = cleaned.replaceAll("\n{2,}", "\n");
        cleaned = cleaned.replaceAll("[^\\x20-\\x7EżźćńółęąśŁŻŹĆŃÓŁŚĄĘ\\n]+", "");
        cleaned = cleaned.replaceAll("(?<! )-\\s+", "");
        return cleaned.trim();
    }

    private String extractAbstract(String text) {
        String flatText = text.replaceAll("\n", " ");
        Pattern startPattern = Pattern.compile("\\bstreszczenie[:\\s]*", Pattern.CASE_INSENSITIVE);
        Pattern endPattern = Pattern.compile("\\babstract\\b", Pattern.CASE_INSENSITIVE);
        return getString(flatText, startPattern, endPattern);
    }

    private String getString(String flatText, Pattern startPattern, Pattern endPattern) {
        Matcher startMatcher = startPattern.matcher(flatText);
        Matcher endMatcher = endPattern.matcher(flatText);

        int startIndex = startMatcher.find() ? startMatcher.end() : -1;
        int endIndex = endMatcher.find() ? endMatcher.start() : -1;

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return flatText.substring(startIndex, endIndex).trim();
        } else if (startIndex != -1) {
            return flatText.substring(startIndex).trim();
        }

        return "";
    }

    private String extractKeywords(String text) {
        String flatText = text.replaceAll("\n", " ");

        Pattern startPattern = Pattern.compile("\\bsłowa\\s*kluczowe[:\\s]*", Pattern.CASE_INSENSITIVE);
        Pattern endPattern = Pattern.compile("\\bkeywords\\b", Pattern.CASE_INSENSITIVE);

        return getString(flatText, startPattern, endPattern);
    }

    private String extractTitle(String text) {
        Pattern startPattern = Pattern.compile("\\bkrit\\s*2025\\s*", Pattern.CASE_INSENSITIVE);
        Pattern endPattern = Pattern.compile("\n");

        Matcher startMatcher = startPattern.matcher(text);
        if (!startMatcher.find()) {
            System.out.println("Brak dopasowania.");
            return "";
        }

        int startIndex = startMatcher.end();
        Matcher endMatcher = endPattern.matcher(text);

        int enterCount = 0;
        int endIndex = text.length();

        while (endMatcher.find()) {
            if (endMatcher.start() >= startIndex) {
                enterCount++;

                if (enterCount == 2) {
                    endIndex = endMatcher.start();
                    break;
                }
            }
        }
        String title = text.substring(startIndex, endIndex).trim();
        title = title.replace("\n", " ").replaceAll(" +", " ").trim();
        return title;
    }

    private String extractAuthors(String text) {
        Pattern startPattern = Pattern.compile("\\bkrit\\s*2025\\s*", Pattern.CASE_INSENSITIVE);
        Matcher startMatcher = startPattern.matcher(text);

        if (!startMatcher.find()) {
            System.out.println("Nie znaleziono 'KRiT 2025'.");
            return "";
        }

        int startIndex = startMatcher.end();

        for (int i = startIndex; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLowerCase(c)) {
                int authorStartIndex = Math.max(i - 1, startIndex);

                int oneCount = 0;
                for (int j = authorStartIndex; j < text.length(); j++) {
                    if (text.charAt(j) == '1') {
                        oneCount++;
                        if (oneCount == 2) {
                            String authorsRaw = text.substring(authorStartIndex, j).trim();
                            return authorsRaw
                                    .replace(";", ",")
                                    .replaceAll("\\d+", "")
                                    .replaceAll(" +", " ");
                        }
                    }
                }

                String authorsRaw = text.substring(authorStartIndex).trim();
                return authorsRaw
                        .replace(";", ",")
                        .replaceAll("\\d+", "")
                        .replaceAll(" +", " ");
            }
        }
        return "";
    }
}

