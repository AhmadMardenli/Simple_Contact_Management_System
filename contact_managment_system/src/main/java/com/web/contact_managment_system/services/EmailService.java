package com.web.contact_managment_system.services;

import com.web.contact_managment_system.models.Email;
import com.web.contact_managment_system.models.Phone;
import com.web.contact_managment_system.repositories.EmailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private EntityManager entityManager;
    private EmailRepository emailRepository;

    public EmailService(EntityManager entityManager, EmailRepository emailRepository) {
        this.entityManager = entityManager;
        this.emailRepository = emailRepository;
    }


    public Email findByEmail(String email){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Email> criteriaQuery = criteriaBuilder.createQuery(Email.class);
        Root<Email> root = criteriaQuery.from(Email.class);

// Define the parameter p
        ParameterExpression<String> paramP = criteriaBuilder.parameter(String.class);

// Build the query
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("email"), paramP));

// Execute the query
        TypedQuery<Email> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(paramP, email);
        if(typedQuery.getResultList().isEmpty())
            return null;
        Email email1=typedQuery.getSingleResult();
        return email1;
    }
    public void save(Email email){
        emailRepository.save(email);
    }
    public void deleteContactEmails(long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Email> deleteQuery = criteriaBuilder.createCriteriaDelete(Email.class);
        Root<Email> root = deleteQuery.from(Email.class);
        // Define the parameter id
        ParameterExpression<Long> paramId = criteriaBuilder.parameter(Long.class);
        // Build the delete query
        deleteQuery.where(criteriaBuilder.equal(root.get("contact_id"), paramId));
        // Execute the delete query
        Query typedDeleteQuery = entityManager.createQuery(deleteQuery);
        typedDeleteQuery.setParameter(paramId, id);
        int deletedCount = typedDeleteQuery.executeUpdate();
        return;
    }

    public void deleteEmail(long id){
        Email email=this.findById(id);
        emailRepository.delete(email);
    }
    public Email findById(long id){
        return emailRepository.findById(id).orElse(null);

    }
}
