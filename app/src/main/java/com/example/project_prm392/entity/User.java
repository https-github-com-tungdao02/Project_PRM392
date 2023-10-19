package com.example.project_prm392.entity;

import java.util.ArrayList;

public class User {
    private int id;
    private String user;
    private  String password;
    private int role;
    private String phone;
    private String address;
    private String image;
    private String gender;

    public ArrayList<User> users;
    public ArrayList<Book> books;


    public User() {
    }

    public User(int id, String user, String password, int role, String phone, String address, String image, String gender, ArrayList<User> users, ArrayList<Book> books) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.gender = gender;
        this.users = users;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
