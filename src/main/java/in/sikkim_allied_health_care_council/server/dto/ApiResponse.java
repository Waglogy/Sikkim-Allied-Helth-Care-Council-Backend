package in.sikkim_allied_health_care_council.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Generic envelope for all API responses so the client always receives a
 * consistent, predictable shape: {@code { success, message, data, timestamp }}.
 *
 * @param <T> type of the payload carried in {@code data}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        Instant timestamp
) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null, Instant.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}
