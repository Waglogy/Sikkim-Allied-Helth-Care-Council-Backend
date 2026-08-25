package in.sikkim_allied_health_care_council.server.dto.upload;

/**
 * Result of a successful upload. The {@code url} is the public address the file
 * is served from — this is what gets stored in the announcement tables.
 */
public record UploadResponse(
        String url,
        String fileName,
        String contentType,
        long size
) {
}
