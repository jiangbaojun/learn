package com.mrk.learn.conversion.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Pdf2Img {
    private static final int SEPARATE_DISTANCE = 10;
    @Test
    public void t1() {
        File sourceFile = new File("C:\\Users\\lx\\Desktop\\t5.pdf");
        File destFile = new File("C:\\Users\\lx\\Desktop\\img.png");
        if (sourceFile.exists()) {
            try {
                PDDocument doc = PDDocument.load(sourceFile);
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                BufferedImage imageNew = null;
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 284);
                    int width = image.getWidth();
                    int height = image.getHeight();
                    if (imageNew == null) {
                        imageNew = new BufferedImage(width, (height + SEPARATE_DISTANCE) * pageCount,
                                BufferedImage.TYPE_INT_RGB);
                    }
                    int[] imageRgbArray = new int[width * height];
                    imageRgbArray = image.getRGB(0, 0, width, height, imageRgbArray, 0, width);
                    imageNew.setRGB(0, (height + SEPARATE_DISTANCE) * i, width, height, imageRgbArray, 0, width);// SEPARATE_DISTANCE表示两张图片的间隔距离
                }
                ImageIO.write(imageNew, "PNG", destFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
