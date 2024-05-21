package com.web.contact_managment_system.repositories;

import com.web.contact_managment_system.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
