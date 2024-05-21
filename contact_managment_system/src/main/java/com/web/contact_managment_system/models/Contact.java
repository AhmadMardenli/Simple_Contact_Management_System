package com.web.contact_managment_system.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public Contact() {
    }

    public Contact(long id, String name, String location, long userId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.userId = userId;
    }

    private String name;
    private String location;
    @Column(name = "user_id")
    private long userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    List<Email>emails=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    List<Phone>phones=new ArrayList<>();

    public Contact(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Contact(String name, String location, long userId) {
        this.name = name;
        this.location = location;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
