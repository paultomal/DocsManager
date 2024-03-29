package com.example.document_management_tool.exceptions;

public class UserNameAlreadyTakenException extends Exception{
    public UserNameAlreadyTakenException(String msg){
        super(msg);
    }
}
