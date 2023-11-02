package com.example.project_prm392.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Community implements Comparable<Community>{

    public UUID firebaseId;
    private String description;
    private Date date;

    private int like;

    public int user_id;
    public int book_id;
    public User user;
    public Book book;

    public Community() {
    }
    public Community( String description, Date date, int like) {
        this.description = description;
        this.date = date;
        this.like = like;
    }

    public Community( String description, int like,int user_id,int book_id) {
        this.description = description;
        this.like = like;
        this.user_id = user_id;
        this.book_id = book_id;
    }

    public Community(String description, Date date, int like, User user, Book book) {
        this.description = description;
        this.date = date;
        this.like = like;
        this.user = user;
        this.book = book;
    }


    public String getDescription() {
        return description;
    }

    public Date getDate() {
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
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

    @Override
    public int compareTo(Community community) {
        return community.getLike() - this.getLike();
    }
}
