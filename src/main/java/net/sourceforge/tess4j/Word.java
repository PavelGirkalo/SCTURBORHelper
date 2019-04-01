package net.sourceforge.tess4j;

import java.awt.Rectangle;

/**
 * Encapsulates Tesseract OCR results at certain page iterator level.
 */
public class Word {

    private final String text;
    private final float confidence;
    private final Rectangle rect;

    /**
     * Constructor.
     * 
     * @param text
     * @param confidence
     * @param boundingBox 
     */
    public Word(String text, float confidence, Rectangle boundingBox) {
        this.text = text;
        this.confidence = confidence;
        this.rect = boundingBox;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the confidence
     */
    public float getConfidence() {
        return confidence;
    }

    /**
     * @return the bounding box
     */
    public Rectangle getBoundingBox() {
        return rect;
    }

    @Override
    public String toString() {
        return String.format("%s [Confidence: %f Bounding box: %d %d %d %d]", text, confidence, rect.x, rect.y, rect.width, rect.height);
    }
}
