
package com.mrk.learn.conversion.jod;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * 老版本用法，不推荐
 *     <dependency>
 *       <groupId>com.artofsolving</groupId>
 *       <artifactId>jodconverter</artifactId>
 *       <version>2.2.1</version>
 *     </dependency>
 */
public class JodTestOld {

    private String base = "D:/myProject/learn/learn/office-opts/src/main/resources/jod/";

    @Test
    public void t1(){
        officeToPDF(base+"t1.doc", base+"t1.pdf");
    }

    /**
     * office转pdf (手动启服务soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;")
     *
     * @param sourceFile 源文件 office
     * @param destFile   目标文件 pdf
     * @return
     */
    public void officeToPDF(String sourceFile, String destFile) {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                // 找不到源文件, 则返回
                return;
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //如果目标文件存在，则删除
            if (outputFile.exists()) {
                outputFile.delete();
            }
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);//"127.0.0.1", 8100
            connection.connect();

            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
