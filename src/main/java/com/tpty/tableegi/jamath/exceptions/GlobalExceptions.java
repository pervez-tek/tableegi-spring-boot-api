package com.tpty.tableegi.jamath.exceptions;

import com.tpty.tableegi.jamath.dto.ErrorResponse;
import jakarta.mail.MessagingException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptions {

    private static final Pattern DUP_KEY_PATTERN =
            Pattern.compile("Key \\((.*?)\\)=\\((.*?)\\)");

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(InvalidDataException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"NO DATA", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoMasjidFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoMasjidFound(NoMasjidFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream().
                map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleEmailExceptions(MessagingException ex) {
        var error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        // Log full exception for diagnostics
        // (use your logger, e.g., log.error("DB error", ex))
        Throwable root = ex.getRootCause();

        // 1) Hibernate ConstraintViolationException
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) ex.getCause();
            String sqlState = cve.getSQLState();
            if ("23505".equals(sqlState)) {
                String friendly = buildDuplicateMessage(root);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponse(HttpStatus.CONFLICT.value(), "DUPLICATE",
                                friendly));//, root == null ? null : root.getMessage()));
            }
        }

        // Generic fallback
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "",
                        "DataIntegrityViolation Invalid data or constraint violation" + ex.getMessage()));
    }

    private String buildDuplicateMessage(Throwable root) {
        if (root == null) return "Duplicate resource already exists";
        String msg = root.getMessage();
        if (msg == null) return "Duplicate resource already exists";

        Matcher m = DUP_KEY_PATTERN.matcher(msg);
        if (m.find()) {
            String key = m.group(1);   // e.g., name, address
            String value = m.group(2); // e.g., Masjid-e-Quba, Hyderabad, Telangana
            return String.format("A record with %s = %s already exists.", key, value);
        }
        // fallback friendly message
        return "A record with the same unique fields already exists.";
    }

}



