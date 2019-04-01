package net.sourceforge.tess4j.util;

import java.io.File;
import java.io.IOException;


public class PdfUtilities {
/*
    public static final String PDF_LIBRARY = "pdf.library";
    public static final String PDFBOX = "pdfbox";


    public static File convertPdf2Tiff(File inputPdfFile) throws IOException {
        if (PDFBOX.equals(System.getProperty(PDF_LIBRARY))) {
            return PdfBoxUtilities.convertPdf2Tiff(inputPdfFile);
        } else {
            try {
                return PdfGsUtilities.convertPdf2Tiff(inputPdfFile);
            } catch (Exception e) {
                System.setProperty(PDF_LIBRARY, PDFBOX);
                return convertPdf2Tiff(inputPdfFile);
            }
        }
    }


    public static File[] convertPdf2Png(File inputPdfFile) throws IOException {
        if (PDFBOX.equals(System.getProperty(PDF_LIBRARY))) {
            return PdfBoxUtilities.convertPdf2Png(inputPdfFile);
        } else {
            try {
                return PdfGsUtilities.convertPdf2Png(inputPdfFile);
            } catch (Exception e) {
                System.setProperty(PDF_LIBRARY, PDFBOX);
                return convertPdf2Png(inputPdfFile);
            }
        }
    }


    public static void splitPdf(String inputPdfFile, String outputPdfFile, String firstPage, String lastPage) {
        if (firstPage.trim().isEmpty()) {
            firstPage = "0";
        }
        if (lastPage.trim().isEmpty()) {
            lastPage = "0";
        }

        splitPdf(new File(inputPdfFile), new File(outputPdfFile), Integer.parseInt(firstPage), Integer.parseInt(lastPage));
    }


    public static void splitPdf(File inputPdfFile, File outputPdfFile, int firstPage, int lastPage) {
        if (PDFBOX.equals(System.getProperty(PDF_LIBRARY))) {
            PdfBoxUtilities.splitPdf(inputPdfFile, outputPdfFile, firstPage, lastPage);
        } else {
            try {
                PdfGsUtilities.splitPdf(inputPdfFile, outputPdfFile, firstPage, lastPage);
            } catch (Exception e) {
                System.setProperty(PDF_LIBRARY, PDFBOX);
                splitPdf(inputPdfFile, outputPdfFile, firstPage, lastPage);
            }
        }
    }


    public static int getPdfPageCount(String inputPdfFile) {
        return getPdfPageCount(new File(inputPdfFile));
    }


    public static int getPdfPageCount(File inputPdfFile) {
        if (PDFBOX.equals(System.getProperty(PDF_LIBRARY))) {
            return PdfBoxUtilities.getPdfPageCount(inputPdfFile);
        } else {
            try {
                return PdfGsUtilities.getPdfPageCount(inputPdfFile);
            } catch (Exception e) {
                System.setProperty(PDF_LIBRARY, PDFBOX);
                return getPdfPageCount(inputPdfFile);
            }
        }
    }


    public static void mergePdf(File[] inputPdfFiles, File outputPdfFile) {
        if (PDFBOX.equals(System.getProperty(PDF_LIBRARY))) {
            PdfBoxUtilities.mergePdf(inputPdfFiles, outputPdfFile);
        } else {
            try {
                PdfGsUtilities.mergePdf(inputPdfFiles, outputPdfFile);
            } catch (Exception e) {
                System.setProperty(PDF_LIBRARY, PDFBOX);
                mergePdf(inputPdfFiles, outputPdfFile);
            }
        }
    }
    */
}
