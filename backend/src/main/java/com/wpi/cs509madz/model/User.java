package com.wpi.cs509madz.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;

    @Column(unique=true, nullable=false)
    private String username;

    private String password;

    private byte[] salt;


    public Integer getId() {

        return ID;
    }

    public void setId(Integer ID) {


        this.ID = ID;
    }

    public String getUsername() {


        return username;
    }

    public void setUsername(String username) {


        this.username = username;
    }

    public String getPassword() {


        return password;
    }

    public void setPassword(String password) {


        this.password = password;
    }

    public byte[] getSalt() {

        return salt;
    }

    public void setSalt(byte[] salt) {

        this.salt = salt;
    }
}
