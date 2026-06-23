package com.example.Multi.Vender.Food.Order.Web.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String apiKey;

    @Value("${supabase.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) throws Exception {

        String originalName = file.getOriginalFilename();

        String fileName =
                UUID.randomUUID() + "_" +
                        originalName.replaceAll("[^a-zA-Z0-9._-]", "_");

        String uploadUrl =
                supabaseUrl +
                        "/storage/v1/object/" +
                        bucket +
                        "/" +
                        fileName;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadUrl))
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type",
                        file.getContentType())
                .POST(HttpRequest.BodyPublishers
                        .ofByteArray(file.getBytes()))
                .build();

        HttpClient.newHttpClient()
                .send(request,
                        HttpResponse.BodyHandlers.ofString());

        return supabaseUrl +
                "/storage/v1/object/public/" +
                bucket +
                "/" +
                fileName;
    }
}