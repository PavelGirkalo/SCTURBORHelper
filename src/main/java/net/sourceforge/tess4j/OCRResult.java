package net.sourceforge.tess4j;

import java.util.List;


public class OCRResult {

    private final int confidence;

    private final List<Word> words;


    public OCRResult(int confidence, List<Word> words) {
        this.confidence = confidence;
        this.words = words;
    }


    public int getConfidence() {
        return confidence;
    }


    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "Average Text Confidence: " + getConfidence() + "% Words: " + getWords().toString();
    }
}
