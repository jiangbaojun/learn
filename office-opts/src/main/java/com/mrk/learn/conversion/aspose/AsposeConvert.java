package com.mrk.learn.conversion.aspose;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsposeConvert {

    @Test
    public void t1() {
        String inPath = "C:\\Users\\lx\\Desktop\\t1.docx";
        String outPath = "C:\\Users\\lx\\Desktop\\target.pdf";
        FileOutputStream os =null;
        try {
            File file = new File(outPath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
