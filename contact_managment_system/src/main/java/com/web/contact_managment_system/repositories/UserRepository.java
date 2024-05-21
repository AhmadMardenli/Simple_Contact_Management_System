package com.web.contact_managment_system.repositories;

import com.web.contact_managment_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findUserByUsername(String username);
}
