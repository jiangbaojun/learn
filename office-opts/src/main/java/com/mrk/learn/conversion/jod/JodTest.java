
package com.mrk.learn.conversion.jod;


import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 新版本用法
 *     <dependency>
 *       <groupId>org.jodconverter</groupId>
 *       <artifactId>jodconverter-local</artifactId>
 *       <version>4.4.2</version>
 *     </dependency>
 */
public class JodTest {

    private String base = "D:/myProject/learn/learn/office-opts/src/main/resources/jod/";

    @Test
    public void t1(){
        office2PDF(base+"t1.docx", base+"t1.pdf");
    }
    @Test
    public void t2(){
        office2PDF(base+"t2.xlsx", base+"t2.pdf");
    }
    @Test
    public void t3(){
        office2PDF(base+"t3.pptx", base+"t3.pdf");
    }

    @Test
    public void toHtml(){
        office2PDF(base+"t1.docx", base+"html/t1/t1.html");
        office2PDF(base+"t2.xlsx", base+"html/t2/t2.html");
        office2PDF(base+"t3.pptx", base+"html/t3/t3.html");
    }

    public boolean office2PDF(String sourceFile, String destFile) {
        OfficeManager officeManager
                = LocalOfficeManager.builder().install()
                .officeHome("C:\\Program Files (x86)\\OpenOffice 4")
                .build();
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                System.out.println("找不到源文件");
                return false;// 找不到源文件, 则返回-1
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //启动office服务
            officeManager.start();
            JodConverter.convert(new File(sourceFile)).to(outputFile).execute();

//            LocalConverter
//                    .builder()
//                    //还是要加上‘页数’过滤器才行
//                    .filterChain(new PageMarginsFilter(10,10,10,30))
//                    .build()
//                    .convert(new File(sourceFile))
//                    .to(outputFile)
//                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OfficeUtils.stopQuietly(officeManager); // Stop the office process
        }
        return true;
    }
}
