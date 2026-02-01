package com.juniorg.custom_exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String message) { super(message); }
}
