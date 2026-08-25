package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.services.storage.StorageService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * Public read-only proxy that streams uploaded files back from R2. This lets
 * the browser show images / open documents without the R2 bucket being public.
 * Registered as a permit-all route in SecurityConfig so {@code <img>} tags work.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{*key}")
    public ResponseEntity<byte[]> serve(@PathVariable String key) {

        // The {*key} capture keeps the leading slash — strip it for the S3 key.
        String objectKey = key.startsWith("/") ? key.substring(1) : key;

        StorageService.StoredFile file = storageService.download(objectKey);

        MediaType mediaType = file.contentType() != null
                ? MediaType.parseMediaType(file.contentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .contentType(mediaType)
                .cacheControl(CacheControl.maxAge(Duration.ofDays(365)).cachePublic())
                .body(file.bytes());
    }
}
