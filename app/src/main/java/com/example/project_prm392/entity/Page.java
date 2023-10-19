package com.example.project_prm392.entity;

public class Page {
    private int id;
    private String content;
    public Book book;

    public Page() {
    }

    public Page(int id, String content, Book book) {
        this.id = id;
        this.content = content;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
