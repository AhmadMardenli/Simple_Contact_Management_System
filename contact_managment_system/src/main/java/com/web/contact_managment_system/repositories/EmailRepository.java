package com.web.contact_managment_system.repositories;

import com.web.contact_managment_system.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {
}
