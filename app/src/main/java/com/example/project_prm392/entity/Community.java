package com.example.project_prm392.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Community {
    private int id;
    private String description;
    private String date;

    private int like;

    public int userId;
    public int book_id;
    public User user;
    public Book book;

    public Community() {
    }

    public Community(int id, String description, String date, int like) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.like = like;
    }

    public Community( String description, String date, int like) {
        this.description = description;
        this.date = date;
        this.like = like;
    }

    public Community(int id, String description, String date, int like, User user, Book book) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.like = like;
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getLike() {
        return like;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}