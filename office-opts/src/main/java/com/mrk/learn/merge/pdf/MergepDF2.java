package com.mrk.learn.merge.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * PDF合并
 * 使用pdfbox
 */
public class MergepDF2 {

    @Test
    public void t2() throws IOException {
//        //Loading an existing PDF document
//        File file1 = new File("C:\\Users\\lx\\Desktop\\t1.pdf");
//        PDDocument doc1 = PDDocument.load(file1);
//        File file2 = new File("C:\\Users\\lx\\Desktop\\t2.pdf");
//        PDDocument doc2 = PDDocument.load(file2);
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName("C:\\Users\\lx\\Desktop\\merged.pdf");
        PDFmerger.addSource(new File("C:\\Users\\lx\\Desktop\\1\\file0.pdf"));
        PDFmerger.addSource(new File("C:\\Users\\lx\\Desktop\\1\\file1.pdf"));
        PDFmerger.addSource(new File("C:\\Users\\lx\\Desktop\\1\\file2.pdf"));
        PDFmerger.addSource(new File("C:\\Users\\lx\\Desktop\\1\\file3.pdf"));
        PDFmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        System.out.println("Documents merged");
//        doc1.close();
//        doc2.close();
    }
}
