package com.example.aman.whatsapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {


    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "message_owner")
    private int messageOwner;

    public Task(String message, int messageOwner) {
        this.message = message;
        this.messageOwner = messageOwner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(int messageOwner) {
        this.messageOwner = messageOwner;
    }
}
