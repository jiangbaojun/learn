package com.mrk.learn.merge.pdf;

/**
 * PDF合并
 * 使用itext5的写法
 */
public class MergepDF3 {

//    @Test
//    public void t1() throws IOException {
//        String[] files = new String[]{
//                "C:\\Users\\lx\\Desktop\\t2.pdf",
//                "C:\\Users\\lx\\Desktop\\t1.pdf",
//                "C:\\Users\\lx\\Desktop\\t3.pdf",
//        };
//        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\lx\\Desktop\\MergeDocument.pdf"));
//        Document document = new Document();// 创建一个新的PDF
//        try {
//            PdfCopy copy = new PdfCopy(document, fos);
//            document.open();
//            for (String file : files) {// 取出单个PDF的数据
//                PdfReader reader = new PdfReader(file);
//                int pageTotal= reader.getNumberOfPages();
////                logger.info("pdf的页码数是 ==> {}",pageTotal);
//                for (int pageNo=1;pageNo<=pageTotal;pageNo++){
//                    document.newPage();
//                    PdfImportedPage page = copy.getImportedPage(reader, pageNo);
//                    copy.addPage(page);
//                }
//                reader.close();
//            }
//            document.close();
//            fos.close();
//            copy.close();
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public byte[] t2() throws IOException {
//        String[] files = new String[]{
//                "C:\\Users\\lx\\Desktop\\t2.pdf",
//                "C:\\Users\\lx\\Desktop\\t1.pdf",
//                "C:\\Users\\lx\\Desktop\\t3.pdf",
//        };
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Document document = new Document();// 创建一个新的PDF
//        byte[] pdfs = new byte[0];
//        try {
//            PdfCopy copy = new PdfCopy(document, bos);
//            document.open();
//            for (String file : files) {// 取出单个PDF的数据
//                PdfReader reader = new PdfReader(file);
//                int pageTotal= reader.getNumberOfPages();
////                logger.info("pdf的页码数是 ==> {}",pageTotal);
//                for (int pageNo=1;pageNo<=pageTotal;pageNo++){
//                    document.newPage();
//                    PdfImportedPage page = copy.getImportedPage(reader, pageNo);
//                    copy.addPage(page);
//                }
//                reader.close();
//            }
//            document.close();
//            pdfs = bos.toByteArray();
//            bos.close();
//            copy.close();
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//        return pdfs;
//    }
}
