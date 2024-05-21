package com.web.contact_managment_system.models;

import jakarta.persistence.*;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Email() {
    }

    @Column(unique = true)
    private String email;
    @Column(name = "contact_id")
    private long contactId;

    public Email(String email, long contactId) {
        this.email = email;
        this.contactId = contactId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
