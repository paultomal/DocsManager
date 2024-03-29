package com.example.document_management_tool.exceptions;

public class EmailAlreadyTakenException extends Exception {
    public EmailAlreadyTakenException(String msg) {
        super(msg);
    }
}
