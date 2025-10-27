package com.juniorg.custom_exception;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.juniorg.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Helper to build ErrorResponse
	private ErrorResponse buildError(HttpServletRequest req, HttpStatus status, String message, String error) {
		ErrorResponse er = new ErrorResponse(Instant.now(), status.value(), error, message, req.getRequestURI());
		return er;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
		log.warn("Malformed JSON request: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, "Malformed JSON request", "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
		String msg = String.format("Invalid value for parameter '%s': %s", ex.getName(), ex.getValue());
		log.warn("Type mismatch: {}", msg);
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, msg, "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest req) {
		String msg = String.format("Missing parameter: %s", ex.getParameterName());
		log.warn(msg);
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, msg, "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
		log.warn("Constraint violations: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, "Validation failed: " + ex.getMessage(), "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
		log.warn("Method not allowed: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), "Method Not Allowed");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
		log.warn("Access denied: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.FORBIDDEN, "You do not have permission to access this resource", "Forbidden");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(HttpMessageNotWritableException.class)
	public ResponseEntity<ErrorResponse> handleMessageNotWritable(HttpMessageNotWritableException ex, HttpServletRequest req) {
		// This is the JSON serialization error (LazyInitializationException can surface here)
		log.error("Serialization error: {}", ex.getMessage(), ex);
		ErrorResponse err = buildError(req, HttpStatus.INTERNAL_SERVER_ERROR, "Response serialization failed", "Server Error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateEntry(DuplicateEntryException ex, HttpServletRequest req) {
		log.warn("Duplicate entry: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.CONFLICT, ex.getMessage(), "Conflict");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}


	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occured: "+ ex.getMessage());
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleDuplicate(IllegalStateException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // Error 409
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // Error 400
	}
	
	// Added to handle the exceptions raised when validating the dto
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
	 }

	@ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(io.jsonwebtoken.ExpiredJwtException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Token expired: " + ex.getMessage());
	}

	@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "Invalid data: " + ex.getRootCause().getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
