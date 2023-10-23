package com.example.project_prm392.entity;

import java.util.Date;

public class History {
    private int id;
    private int page;
    private Date time;
    public Book book;
    public User user;

    public History() {
    }

    public History(int id, int page, Date time, Book book, User user) {
        this.id = id;
        this.page = page;
        this.time = time;
        this.book = book;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
