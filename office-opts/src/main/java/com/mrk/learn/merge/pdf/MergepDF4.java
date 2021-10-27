package com.mrk.learn.merge.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * PDF合并
 * 使用itext7的写法
 */
public class MergepDF4 {

    @Test
    public void t1() throws Exception {
        File destFile = new File("C:\\Users\\lx\\Desktop\\merge.pdf");

        PdfDocument pdf = new PdfDocument(new PdfWriter(destFile));
        PdfMerger merger = new PdfMerger(pdf);

        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(new File("C:\\Users\\lx\\Desktop\\1\\file0.pdf")));
        merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());

        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(new File("C:\\Users\\lx\\Desktop\\1\\file1.pdf")));
        merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());

        PdfDocument pdf3 = new PdfDocument(new PdfReader(new File("C:\\Users\\lx\\Desktop\\1\\file2.pdf")));
        merger.merge(pdf3, 1, pdf3.getNumberOfPages());

        PdfDocument pdf4 = new PdfDocument(new PdfReader(new File("C:\\Users\\lx\\Desktop\\1\\file3.pdf")));
        merger.merge(pdf4, 1, pdf4.getNumberOfPages());

        firstSourcePdf.close();
        secondSourcePdf.close();
        pdf.close();
    }
}
