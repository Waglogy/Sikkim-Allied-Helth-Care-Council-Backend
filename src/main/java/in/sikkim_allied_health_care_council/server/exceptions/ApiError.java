package in.sikkim_allied_health_care_council.server.exceptions;

import java.util.Map;

public record ApiError(
        boolean success,
        String message,
        Map<String, String> errors
) {
}
