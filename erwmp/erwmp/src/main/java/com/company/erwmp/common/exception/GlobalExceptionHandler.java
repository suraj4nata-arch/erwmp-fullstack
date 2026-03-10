package com.company.erwmp.common.exception;

import com.company.erwmp.common.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    // IllegalArgumentException (used for duplicate users)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, String>> handleIllegalArgs(IllegalArgumentException ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    // Catch-all for other exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
//        ex.printStackTrace();
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "Internal Server Error");
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                Instant.now(),    //when the error happened
                HttpStatus.INTERNAL_SERVER_ERROR.value(),    //  eg., 500
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), //Human-readable status eg., "Human-readable status"
                ex.getMessage(), //Actual error message
                request.getRequestURI()   //Which API caused the error, eg., /api/users/16
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);   //Sends the error as JSON
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                Instant.now(),
                HttpStatus.FORBIDDEN.value(),          // 403
                HttpStatus.FORBIDDEN.getReasonPhrase(), // Forbidden
                "You do not have permission to perform this action",
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}

/*

This class:
  -->Listens to all controllers
  -->Automatically catches exceptions

Returns JSON responses
* @RestControllerAdvice
One receptionist handles all exceptions
*
*
------------------------------------------------------------------------
public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {

--Receives the exception object
Returns:
-->A map of errors
-->HTTP 400 (Bad Request)

🧠 Analogy
Manager receives complaint details and prepares a response letter.
-------------------------------------------------------------------------
Map<String, String> errors = new HashMap<>();

🧠 Analogy
Manager opens a notebook to list all mistakes.
-------------------------------------------------------------------------
ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
);
📌 Step-by-step:
Get all invalid fields

For each field:
Field name → error message
Store them in map

🧠 Analogy
-->For every wrong field:
“Email → Invalid format”
“Password → Too short”


* */