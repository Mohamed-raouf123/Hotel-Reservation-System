package com.mycompany.uniproject;
public class InvalidCardException extends RuntimeException {
    public InvalidCardException(String message) {
        super(message);
    }
}