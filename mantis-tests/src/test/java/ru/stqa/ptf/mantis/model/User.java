package ru.stqa.ptf.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;

/**
 * Created by Olga on 15.04.2016.
 */
public class User {

    @Column
    private int id;

    @Column
    @Type(type = "text")
    private String username;

    @Column
    @Type(type = "text")
    private String email;

    @Column
    @Type(type = "text")
    private String password;

    @Column(name = "access_level")
    private int accessLevel;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public User setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }
}
