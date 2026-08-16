package in.sikkim_allied_health_care_council.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> apiErrorResponseEntity(
            MethodArgumentNotValidException exception

    ) {

        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (a, b) -> a
                ));

        return ResponseEntity
                .badRequest()
                .body(new ApiError(
                        false,
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        false,
                        exception.getMessage(),
                        Map.of()
                ));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicateResource(
            DuplicateResourceException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        false,
                        exception.getMessage(),
                        Map.of()
                ));
    }

}
