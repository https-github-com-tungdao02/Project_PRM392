package com.example.project_prm392.entity;

import java.util.Date;

public class History {
    private int id;
    private int page;
    private Date time;
    private Book book;
    private User user;

    private Page number_page;
    public History() {
    }

    public History(int id, int page, Date time, Book book, User user, Page number_page) {
        this.id = id;
        this.page = page;
        this.time = time;
        this.book = book;
        this.user = user;
        this.number_page = number_page;
    }

    public Page getNumber_page() {
        return number_page;
    }

    public void setNumber_page(Page number_page) {
        this.number_page = number_page;
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
