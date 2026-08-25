package in.sikkim_allied_health_care_council.server.services.storage;

import in.sikkim_allied_health_care_council.server.exceptions.InvalidFileException;
import in.sikkim_allied_health_care_council.server.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Uploads files to Cloudflare R2 and serves them back. Only images and PDFs up
 * to 10 MB are accepted; the same limit is enforced at the servlet multipart
 * layer as a first line of defence.
 */
@Service
public class StorageService {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif", "application/pdf"
    );
    private static final long MAX_SIZE_BYTES = 10L * 1024 * 1024;

    private final S3Client r2Client;
    private final String bucket;
    private final String publicBaseUrl;

    public StorageService(
            S3Client r2Client,
            @Value("${app.r2.bucket}") String bucket,
            @Value("${app.r2.public-base-url}") String publicBaseUrl
    ) {
        this.r2Client = r2Client;
        this.bucket = bucket;
        this.publicBaseUrl = publicBaseUrl;
    }

    /** Stores the file in R2 and returns its object key (e.g. uploads/img/…png). */
    public String upload(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("No file provided.");
        }
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new InvalidFileException("File exceeds the 10 MB limit.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new InvalidFileException("Unsupported file type. Allowed: JPG, PNG, WebP, GIF, PDF.");
        }

        // Images → uploads/img, everything else (PDFs) → uploads/documents.
        String folder = contentType.startsWith("image/") ? "uploads/img" : "uploads/documents";
        String key = folder + "/" + UUID.randomUUID() + extensionOf(file.getOriginalFilename());

        try {
            r2Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new InvalidFileException("Could not read the uploaded file.");
        }

        return key;
    }

    /**
     * The direct public URL for a key when a public base URL (r2.dev / custom
     * domain) is configured, or {@code null} to indicate the caller should fall
     * back to serving the file through the backend proxy.
     */
    public String publicUrlFor(String key) {
        if (publicBaseUrl == null || publicBaseUrl.isBlank()) {
            return null;
        }
        return publicBaseUrl.replaceAll("/+$", "") + "/" + key;
    }

    /** Fetches an object's bytes for the download proxy. 404s if it is missing. */
    public StoredFile download(String key) {
        try {
            ResponseBytes<GetObjectResponse> object = r2Client.getObjectAsBytes(
                    GetObjectRequest.builder().bucket(bucket).key(key).build()
            );
            return new StoredFile(object.asByteArray(), object.response().contentType());
        } catch (NoSuchKeyException e) {
            throw new ResourceNotFoundException("File not found.");
        }
    }

    /** A downloaded object: its raw bytes and content type. */
    public record StoredFile(byte[] bytes, String contentType) {
    }

    /** Returns the lowercased extension (with dot) of the original name, or "". */
    private String extensionOf(String originalName) {
        if (originalName == null) return "";
        int dot = originalName.lastIndexOf('.');
        if (dot < 0 || dot == originalName.length() - 1) return "";
        return originalName.substring(dot).toLowerCase();
    }
}
