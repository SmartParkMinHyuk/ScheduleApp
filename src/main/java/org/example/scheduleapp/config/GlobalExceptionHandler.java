package org.example.scheduleapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 예시) ID NOT_FOUND 에러 명시
     * {
     *  "error": "Invalid Request",
     *  "message": "Does not exist writer_id = 5",
     *  "timestamp": "2025-03-25T00:34:50.5752995",
     *  "status": 404
     * }
     **/
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", ex.getStatusCode().value());
        errorResponse.put("error", "Invalid Request");
        errorResponse.put("message", ex.getReason());

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }


    /**
     * Parameter Required Is Null 에러 명시
     * {
     *  "error": "Invalid Request",
     *  "message": "email required",
     *  "timestamp": "2025-03-24T16:33:30.9768155",
     *  "status": 400
     * }
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Invalid Request");

        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        errorResponse.put("message", errorMessages.isEmpty() ? "Validation failed" : errorMessages.get(0));

        return errorResponse;
    }


}
