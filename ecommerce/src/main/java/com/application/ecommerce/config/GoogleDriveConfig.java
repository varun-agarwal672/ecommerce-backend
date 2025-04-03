package com.application.ecommerce.config;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    public Drive getDriveService() throws IOException {
        FileInputStream credentialsStream = new FileInputStream("C:\\credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);
        return new Drive.Builder(new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                .setApplicationName("ecommerce")
                .build();
    }
}
