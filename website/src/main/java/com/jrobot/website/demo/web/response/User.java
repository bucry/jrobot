package com.jrobot.website.demo.web.response;


import javax.persistence.*;

/**
 * Created by twcn on 10/19/16.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;

    @Column(name = "username")
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}