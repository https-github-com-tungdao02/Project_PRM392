package com.example.project_prm392.entity;

import java.util.ArrayList;

public class Book {
    private int id;
    private String name;
    private String description;
    private String author;
    private int like;
    private String image;
    private int views;

    public ArrayList<Community> communities;
    public ArrayList<History> histories;
    public ArrayList<Page> pages;
    public ArrayList<Tag> tags;

    public Book() {
    }

    public Book(int id, String name, String description, String author, int like, String image, int views, ArrayList<Community> communities, ArrayList<History> histories, ArrayList<Page> pages, ArrayList<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.like = like;
        this.image = image;
        this.views = views;
        this.communities = communities;
        this.histories = histories;
        this.pages = pages;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ArrayList<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(ArrayList<Community> communities) {
        this.communities = communities;
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public void setHistories(ArrayList<History> histories) {
        this.histories = histories;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
}
