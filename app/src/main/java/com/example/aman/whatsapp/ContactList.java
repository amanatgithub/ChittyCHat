package com.example.aman.whatsapp;

public class ContactList {
    String name;
    String number;

    public ContactList(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
