package com.example.aman.whatsapp;

public class Message {
    String message;
    int i;

    public Message(String message, int i) {
        this.message = message;
        this.i = i;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public int getI() {
        return i;
    }
}
