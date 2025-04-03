package com.application.ecommerce.google;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/drive")
public interface GoogleDriveApi {

    @PostMapping("/upload")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);

    @GetMapping("/file/{fileId}")
    ResponseEntity<String> getFileUrl(@PathVariable String fileId);
}
