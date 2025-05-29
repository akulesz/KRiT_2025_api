package com.example.KRiT_2025_backend.Pdf;

public class TextLine {
    public String text;
    public double fontSize;

    public TextLine(String text, double fontSize) {
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return "TextLine{" +
                "text='" + text + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }
}
