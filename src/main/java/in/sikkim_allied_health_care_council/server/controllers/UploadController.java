package in.sikkim_allied_health_care_council.server.controllers;

import in.sikkim_allied_health_care_council.server.dto.ApiResponse;
import in.sikkim_allied_health_care_council.server.dto.upload.UploadResponse;
import in.sikkim_allied_health_care_council.server.services.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Uploads a single image / document to R2 and returns its public URL. The
 * caller (admin UI) then stores that URL on an event / message / publication.
 * Requires authentication like every non-{@code /api/auth} route.
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UploadResponse>> upload(
            @RequestParam("file") MultipartFile file
    ) {

        String key = storageService.upload(file);

        // Prefer a configured public R2 URL; otherwise serve via the backend
        // proxy at /api/files/** so previews work without a public bucket.
        String url = storageService.publicUrlFor(key);
        if (url == null) {
            url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/")
                    .path(key)
                    .toUriString();
        }

        UploadResponse response = new UploadResponse(
                url,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("File uploaded successfully", response));
    }
}
