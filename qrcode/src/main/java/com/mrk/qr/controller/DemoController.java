package com.mrk.qr.controller;

import com.mrk.qr.zxing.QRCodeGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     * 返回图片
     */
    @RequestMapping("/img")
    public ResponseEntity<byte[]> img(HttpServletRequest request, HttpServletResponse response){
        byte[] qrCode = null;
        String str = request.getParameter("str");
        try {
            qrCode = QRCodeGenerator.getQRCodeImage(str, 360, 360);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(qrCode, headers, HttpStatus.CREATED);
    }

    /**
     * 返回图片
     */
    @GetMapping("img1")
    public void img1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] qrCode = null;
        try {
            qrCode = QRCodeGenerator.getQRCodeImage(request.getParameter("str"), 360, 360);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.getOutputStream().write(qrCode);
    }

    /**
     * 下载图片
     */
    @GetMapping("img2")
    public void img2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] qrCode = null;
        try {
            qrCode = QRCodeGenerator.getQRCodeImage(request.getParameter("str"), 360, 360);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = new String("我的二维码img2.png".getBytes("UTF-8"), "ISO-8859-1");
        // ContentType不设置也没事
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename", fileName);
        response.getOutputStream().write(qrCode);
    }

    /**
     * 返回base64
     */
    @RequestMapping("/imgCode")
    @ResponseBody
    public String imgCode(HttpServletRequest request, HttpServletResponse response){
        byte[] qrCode = null;
        String str = request.getParameter("str");
        try {
            qrCode = QRCodeGenerator.getQRCodeImage(str, 360, 360);
            String img = Base64.getEncoder().encodeToString(qrCode);
            return "data:image/png;base64,"+img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nothing";
    }
}
