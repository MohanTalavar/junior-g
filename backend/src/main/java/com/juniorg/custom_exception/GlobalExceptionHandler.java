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
	public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
		log.warn("Bad credentials: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.UNAUTHORIZED, "Invalid username or password", "Unauthorized");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		log.warn("Resource not found: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.NOT_FOUND, ex.getMessage(), "Not Found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest req) {
		log.error("Unexpected error: {}", ex.getMessage(), ex);
		ErrorResponse err = buildError(req, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", "Server Error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex, HttpServletRequest req) {
		log.warn("Illegal state: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.CONFLICT, ex.getMessage(), "Conflict");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
		log.warn("Invalid argument: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, ex.getMessage(), "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
		});
		String msg = sb.toString().trim();
		log.warn("Validation failed: {}", msg);
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, msg, "Validation Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleExpiredJwt(io.jsonwebtoken.ExpiredJwtException ex, HttpServletRequest req) {
		log.warn("Expired JWT token: {}", ex.getMessage());
		ErrorResponse err = buildError(req, HttpStatus.UNAUTHORIZED, "Token expired", "Unauthorized");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex, HttpServletRequest req) {
		String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
		log.error("Data integrity violation: {}", rootMsg);
		ErrorResponse err = buildError(req, HttpStatus.BAD_REQUEST, "Invalid data: " + rootMsg, "Bad Request");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
