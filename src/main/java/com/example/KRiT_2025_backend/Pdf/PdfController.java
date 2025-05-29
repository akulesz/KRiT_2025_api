package com.example.KRiT_2025_backend.Pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    public static final Map<String, String> fileNameToTitleMap = new HashMap<>();

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractPdfData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nieprawidłowy plik PDF"));
        }

        try {
            String userHome = System.getProperty("user.home");
            String uploadDir = userHome + "/uploads/";

            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            String uniqueFileName = java.util.UUID.randomUUID() + "_" + file.getOriginalFilename();
            java.nio.file.Path filePath = uploadPath.resolve(uniqueFileName);
            file.transferTo(filePath.toFile());

            // 2. Wczytaj plik PDF z dysku zamiast z InputStream
            try (PDDocument document = PDDocument.load(filePath.toFile())) {
                PDFTextStripper stripper = new PDFTextStripper();
                String rawText = stripper.getText(document);
                System.out.println("rawtext = " + rawText);
                String text = cleanText(rawText);
                System.out.println("text = " + text);

                FontAwarePDFTextStripper fontStripper = new FontAwarePDFTextStripper();
                fontStripper.getText(document);
                List<TextLine> lines = fontStripper.getTextLines();
                for (TextLine line : lines) {
                    System.out.println("line = " + line.text);
                }

                Map<String, String> result = new HashMap<>();
                result.put("abstract", extractAbstract(text));
                result.put("keywords", extractKeywords(text));
                result.put("title", extractTitle2(lines));
                result.put("authors", extractAuthors(lines));

                String extractedTitle = extractTitle(lines);

                PDDocumentInformation newInfo = new PDDocumentInformation();
                newInfo.setTitle(extractedTitle);
                newInfo.setAuthor("KRiT 2025");
                document.setDocumentInformation(newInfo);

                document.save(filePath.toFile());

                fileNameToTitleMap.put(uniqueFileName, extractedTitle);

                String baseUrl2 = "http://172.20.10.4:8080";
                String baseUrl1 = "http://10.0.2.2:8080";
                String baseUrl = "http://localhost:8080";
                String pdfUrl = baseUrl + "/uploads/" + uniqueFileName;
                result.put("pdfUrl", pdfUrl);

                return ResponseEntity.ok(result);
            }
        } catch (IOException e) {
            e.printStackTrace(); // dodaj logowanie błędu
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Błąd podczas przetwarzania PDF"));
        }

    }

    private String cleanText(String text) {

        String cleanedText = text.replaceAll("[ \\t\\r\\f\\v]+", " ").trim();

        cleanedText = cleanedText.replaceAll("[^\\x20-\\x7EżźćńółęąśŁŻŹĆŃÓŁŚĄĘ\\n]+", "");

        return cleanedText;
    }

    //private String extractAbstract(String text) {
//        return extractBetween(text, "Streszczenie", "Słowa kluczowe");
//    }

//    private String extractKeywords(String text) {
//        return extractAfter(text, "Słowa kluczowe");
//    }

//    private String extractTitle(String text) {
//        String[] lines = text.split("\n");
//        for (String line : lines) {
//            if (line.trim().toUpperCase().equals(line.trim()) && line.trim().length() > 10) {
//                return line.trim();
//            }
//        }
//        return "";
//    }
//
//    private String extractAuthors(String text) {
//        String[] lines = text.split("\n");
//        for (int i = 0; i < Math.min(lines.length, 20); i++) {
//            if (lines[i].toLowerCase().contains("krit 2025") && i + 1 < lines.length) {
//                return lines[i + 1].trim();
//            }
//        }
//        return "";
//    }

//    private String extractBetween(String text, String start, String end) {
//        int startIdx = text.toLowerCase().indexOf(start.toLowerCase());
//        int endIdx = text.toLowerCase().indexOf(end.toLowerCase());
//        if (startIdx >= 0 && endIdx > startIdx) {
//            return text.substring(startIdx + start.length(), endIdx).trim();
//        }
//        return "";
//    }
//
//    private String extractAfter(String text, String marker) {
//        int index = text.toLowerCase().indexOf(marker.toLowerCase());
//        if (index >= 0) {
//            String rest = text.substring(index + marker.length());
//            return rest.split("\n")[0].trim();
//        }
//        return "";
//    }

    private String extractAbstract(String text) {
        String flatText = text.replaceAll("\n", " ");

        Pattern startPattern = Pattern.compile("\\bstreszczenie[:\\s]*", Pattern.CASE_INSENSITIVE);
        Pattern endPattern = Pattern.compile("\\babstract\\b", Pattern.CASE_INSENSITIVE);

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

    private boolean isMostlyUpperCase(String text) {
        String[] words = text.trim().split("\\s+");
        int upperCount = 0;
        for (String word : words) {
            if (!word.isEmpty() && word.equals(word.toUpperCase())) {
                upperCount++;
            }
        }
        return words.length > 0 && ((double) upperCount / words.length) > 0.8;
    }

    private int skipUntilKrit(List<TextLine> textLines) {
        for (int i = 0; i < textLines.size(); i++) {
            if (textLines.get(i).text.toLowerCase().contains("krit 2025")) {
                return i + 1;
            }
        }
        return textLines.size();
    }

    private String extractTitle(List<TextLine> textLines) {
        List<String> titleLines = new ArrayList<>();
        Double maxFontSize = null;
        int startIndex = skipUntilKrit(textLines);

        for (int i = startIndex; i < textLines.size(); i++) {
            TextLine line = textLines.get(i);
            if (maxFontSize == null) {
                maxFontSize = line.fontSize;
                titleLines.add(line.text.trim());
            } else if (line.fontSize >= maxFontSize) {
                maxFontSize = line.fontSize;
                titleLines.add(line.text.trim());
            } else {
                break;
            }
        }

        return String.join(" ", titleLines).trim();
    }

    private String extractTitle2(List<TextLine> textLines) {
        List<String> titleLines = new ArrayList<>();
        Double maxFontSize = null;
        int startIndex = skipUntilKrit(textLines);

        System.out.println("=== DEBUG extractTitle ===");
        System.out.println("startIndex po skipUntilKrit: " + startIndex);
        System.out.println("Całkowita liczba linii: " + textLines.size());

        // Debug: wyświetl pierwsze kilka linii po "KRiT 2025"
        for (int i = startIndex; i < Math.min(startIndex + 10, textLines.size()); i++) {
            TextLine line = textLines.get(i);
            System.out.println("Linia " + i + ": '" + line.text + "' (fontSize: " + line.fontSize + ")");
        }

        // Znajdź maksymalny rozmiar czcionki w pierwszych 10 liniach po "KRiT 2025"
        double actualMaxFontSize = 0;
        for (int i = startIndex; i < Math.min(startIndex + 10, textLines.size()); i++) {
            TextLine line = textLines.get(i);
            if (!line.text.trim().isEmpty() && line.fontSize > actualMaxFontSize) {
                actualMaxFontSize = line.fontSize;
            }
        }

        System.out.println("Maksymalny rozmiar czcionki znaleziony: " + actualMaxFontSize);

        // Zbierz wszystkie linie z maksymalnym rozmiarem czcionki
        for (int i = startIndex; i < textLines.size(); i++) {
            TextLine line = textLines.get(i);

            // Pomiń puste linie
            if (line.text.trim().isEmpty()) {
                continue;
            }

            System.out.println("Sprawdzam linię: '" + line.text + "' (fontSize: " + line.fontSize + ")");

            // Jeśli to pierwsza niepusta linia lub ma maksymalny rozmiar czcionki
            if (maxFontSize == null) {
                maxFontSize = line.fontSize;
                titleLines.add(line.text.trim());
                System.out.println("Dodano pierwszą linię do tytułu: '" + line.text.trim() + "'");
            } else if (Math.abs(line.fontSize - actualMaxFontSize) < 0.1) { // tolerancja na różnice w rozmiarze
                titleLines.add(line.text.trim());
                System.out.println("Dodano linię do tytułu: '" + line.text.trim() + "'");
            } else if (line.fontSize < actualMaxFontSize - 1) { // znacząco mniejsza czcionka
                System.out.println("Przerwano - znaleziono mniejszą czcionkę");
                break;
            }

            // Przerwij jeśli znalazłeś słowa kluczowe wskazujące na koniec tytułu
            if (line.text.toLowerCase().contains("streszczenie") ||
                    line.text.toLowerCase().contains("abstract") ||
                    line.text.toLowerCase().contains("wprowadzenie")) {
                System.out.println("Przerwano - znaleziono słowo kluczowe końca tytułu");
                break;
            }
        }

        String result = String.join(" ", titleLines).trim();
        System.out.println("Wynikowy tytuł: '" + result + "'");
        System.out.println("=== KONIEC DEBUG ===");

        return result;
    }

    // Alternatywna wersja - bardziej elastyczna
    private String extractTitleAlternative(List<TextLine> textLines) {
        int startIndex = skipUntilKrit(textLines);

        // Znajdź największy rozmiar czcionki w pierwszych 15 liniach
        double maxFontSize = 0;
        for (int i = startIndex; i < Math.min(startIndex + 15, textLines.size()); i++) {
            TextLine line = textLines.get(i);
            if (!line.text.trim().isEmpty() && line.fontSize > maxFontSize) {
                maxFontSize = line.fontSize;
            }
        }

        // Zbierz wszystkie linie z największą czcionką (z małą tolerancją)
        List<String> titleLines = new ArrayList<>();
        for (int i = startIndex; i < textLines.size(); i++) {
            TextLine line = textLines.get(i);

            if (line.text.trim().isEmpty()) continue;

            // Jeśli rozmiar czcionki jest blisko maksymalnego (tolerancja 0.5pt)
            if (Math.abs(line.fontSize - maxFontSize) <= 0.5) {
                titleLines.add(line.text.trim());
            } else if (!titleLines.isEmpty()) {
                // Jeśli już mamy jakieś linie tytułu i natrafiamy na mniejszą czcionkę, kończymy
                break;
            }

            // Zabezpieczenie - nie więcej niż 5 linii tytułu
            if (titleLines.size() >= 5) break;
        }

        return String.join(" ", titleLines).trim();
    }


    private String extractAuthors(List<TextLine> textLines) {
        List<String> authorLines = new ArrayList<>();
        double maxFontSize = 0;
        double prevFontSize = 0;

        int startIndex = skipUntilKrit(textLines);

        for (int i = startIndex; i < textLines.size(); i++) {
            TextLine line = textLines.get(i);

            if (!line.text.isEmpty() && !isMostlyUpperCase(line.text)) {
                maxFontSize = line.fontSize;

                if (prevFontSize > maxFontSize) {
                    return String.join(" ", authorLines).replaceAll(";", ",").trim();
                }

                authorLines.add(line.text.trim());
            }

            prevFontSize = maxFontSize;
        }

        return String.join(" ", authorLines).replaceAll(";", ",").trim();
    }





}

