package com.cjt.consultant.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/download")
public class FileDownloadController {

    @GetMapping("/file")
    @ResponseBody
    public void downloadFile(HttpServletResponse response) throws IOException {
        byte[] fileContent = "Hello, world!".getBytes();

        // Set the content type and attachment header
        String contentType = "text/plain"; // Replace with the appropriate content type
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"file.txt\"");

        // Write the file content to the response
        response.getOutputStream().write(fileContent);
    }

}
