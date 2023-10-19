package com.example.project_prm392.entity;

public class Tag {
    private String name;
    private int id;
    public Book book;

    public Tag() {
    }

    public Tag(String name, int id, Book book) {
        this.name = name;
        this.id = id;
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
