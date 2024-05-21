package com.web.contact_managment_system.repositories;

import com.web.contact_managment_system.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
