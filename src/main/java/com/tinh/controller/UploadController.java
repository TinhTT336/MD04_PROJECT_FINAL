package com.tinh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Controller
@PropertySource("classpath:config.properties")
public class UploadController {
    @Value("/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images/")
    private String path;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/upload")
    public String upload() {
        return "form-upload";
    }

//    @PostMapping("/upload")
//    public String uploadPost(@RequestParam("img") MultipartFile file, HttpServletRequest request) {
//        String fileName = file.getOriginalFilename();
////        cach 1
////        String path=request.getServletContext().getRealPath("uploads/images");
//
////        cach 2
////        String path=servletContext.getRealPath("upload/images");
////        String path = "/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images";
//        File destination = new File(path + fileName);
//        System.out.println(path);
//        System.out.println(destination);
//        try {
////            cach 1
////            file.transferTo(destination);
//
////            cach 2
//            Files.write(destination.toPath(), file.getBytes(), StandardOpenOption.CREATE);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return "form-upload";
//    }

    @PostMapping("/upload")
    public String postUpload(@RequestParam("img") MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        String path = servletContext.getRealPath("uploads/images/");
//        String path = "/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images/";
        File destination = new File(path + fileName);
        System.out.println(path);
        System.out.println(destination);
        try {
            // cach 1
            // file.transferTo(destination);
            // cach 2
            Files.write(destination.toPath(), file.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "form-upload";
    }

}
