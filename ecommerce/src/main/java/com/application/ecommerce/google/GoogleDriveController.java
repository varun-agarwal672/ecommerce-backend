package com.application.ecommerce.google;

import com.application.ecommerce.config.GoogleDriveConfig;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class GoogleDriveController implements GoogleDriveApi {

    private final GoogleDriveConfig googleDriveConfig;

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            Drive driveService = googleDriveConfig.getDriveService();
            File fileMetadata = new File();
            fileMetadata.setName(file.getOriginalFilename());
            FileContent mediaContent = new FileContent(file.getContentType(),
                    java.io.File.createTempFile("temp", null));
            File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id, webViewLink")
                    .execute();
            return ResponseEntity.ok("File uploaded successfully: " + uploadedFile.getWebViewLink());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> getFileUrl(String fileId) {
        try {
            Drive driveService = googleDriveConfig.getDriveService();
            File file = driveService.files().get(fileId).setFields("webViewLink").execute();
            return ResponseEntity.ok("File URL: " + file.getWebViewLink());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error retrieving file: " + e.getMessage());
        }
    }
}
