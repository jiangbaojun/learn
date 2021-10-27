package com.mrk.qr.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class QRCodeGenerator {

    public static void main(String[] args) throws IOException, WriterException {
//        generateQRCodeImage("大家好大家好123", 60, 60, "d:/123.png");

        byte[] bytes = getQRCodeImage("大家好DAJIAHAO123", 60, 60);
        System.out.println(decode(bytes));

        System.out.println(decode("d:/123.png"));
    }

    /**
     * 生成二维码，写到本地
     * @param text     二维码需要包含的信息
     * @param width    二维码宽度
     * @param height   二维码高度
     * @param filePath 图片保存路径
     * @throws WriterException
     * @throws IOException
     */
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * 生成二维码，返回字节流
     * @param text   二维码需要包含的信息
     * @param width  二维码宽度
     * @param height 二维码高度
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    /**
     * 解析二维码
     * @param bytes 二进制数组
     */
    public static String decode(byte[] bytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        return decode(image);
    }
    /**
     * 解析二维码
     * @param imgPath 本地路径
     * @return
     */
    public static String decode(String imgPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imgPath));
        return decode(image);
    }

    /**
     * 解析二维码
     */
    private static String decode( BufferedImage image) {
        Result result = null;
        try {
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
