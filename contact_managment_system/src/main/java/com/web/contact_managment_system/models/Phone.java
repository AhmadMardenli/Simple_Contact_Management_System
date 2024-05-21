package com.web.contact_managment_system.models;

import jakarta.persistence.*;

@Entity
public class Phone {
    @Column(unique = true)
    private String phone;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "contact_id")
    private long contact_id;

    public Phone(String phone, long contact_id) {
        this.phone = phone;
        this.contact_id = contact_id;
    }

    public Phone() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }
}
