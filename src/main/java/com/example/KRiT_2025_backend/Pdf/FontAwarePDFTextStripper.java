package com.example.KRiT_2025_backend.Pdf;


import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FontAwarePDFTextStripper extends PDFTextStripper {

    private final List<TextLine> lines = new ArrayList<>();
    private StringBuilder currentLine = new StringBuilder();
    private List<Double> currentFontSizes = new ArrayList<>();

    public FontAwarePDFTextStripper() throws IOException {
        super.setSortByPosition(true);  // żeby tekst był uporządkowany
    }

    public List<TextLine> getTextLines() {
        return lines;
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        currentLine.setLength(0);  // reset linii
        currentFontSizes.clear();

        for (TextPosition position : textPositions) {
            currentLine.append(position.getUnicode());
            currentFontSizes.add((double) position.getFontSizeInPt());
        }

        double avgFontSize = currentFontSizes.stream()
                .mapToDouble(f -> f)
                .average()
                .orElse(0.0);

        lines.add(new TextLine(currentLine.toString().trim(), avgFontSize));
    }
}

