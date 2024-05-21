package com.web.contact_managment_system.services;

import com.web.contact_managment_system.models.Phone;
import com.web.contact_managment_system.repositories.PhoneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    private PhoneRepository phoneRepository;
    private EntityManager entityManager;
    public PhoneService(PhoneRepository phoneRepository, EntityManager entityManager) {
        this.phoneRepository = phoneRepository;
        this.entityManager = entityManager;
    }
    public Phone findById(long id){
        return phoneRepository.findById(id).orElse(null);
    }
    public Phone findByPhone(String phone){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> criteriaQuery = criteriaBuilder.createQuery(Phone.class);
        Root<Phone> root = criteriaQuery.from(Phone.class);

// Define the parameter p
        ParameterExpression<String> paramP = criteriaBuilder.parameter(String.class);

// Build the query
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("phone"), paramP));

// Execute the query
        TypedQuery<Phone> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(paramP, phone);
        if(typedQuery.getResultList().isEmpty())
            return null;
        return typedQuery.getSingleResult();
    }
    public void save(Phone phone){
        phoneRepository.save(phone);
    }
    public void update(long id,String phone){
        Phone phone1=phoneRepository.findById(id).orElse(null);
        phone1.setPhone(phone);
        phoneRepository.save(phone1);
    }
    public void deleteContactPhones(long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Phone> deleteQuery = criteriaBuilder.createCriteriaDelete(Phone.class);
        Root<Phone> root = deleteQuery.from(Phone.class);
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
    public void delete(String phoString){
        Phone phone=this.findByPhone(phoString);
        phoneRepository.delete(phone);
    }
}
