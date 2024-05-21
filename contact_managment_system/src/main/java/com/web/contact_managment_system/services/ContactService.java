package com.web.contact_managment_system.services;

import com.web.contact_managment_system.models.Contact;
import com.web.contact_managment_system.models.Email;
import com.web.contact_managment_system.models.Phone;
import com.web.contact_managment_system.repositories.ContactRepository;
import com.web.contact_managment_system.repositories.EmailRepository;
import com.web.contact_managment_system.repositories.PhoneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private PhoneService phoneService;
    private EmailService emailService;
    private EntityManager entityManager;

    public ContactService(ContactRepository contactRepository, PhoneService phoneService, EmailService emailService, EntityManager entityManager) {
        this.contactRepository = contactRepository;
        this.phoneService = phoneService;
        this.emailService = emailService;
        this.entityManager = entityManager;
    }

    public void deleteContact(long id){
        contactRepository.deleteById(id);
    }
    public void saveContact(Contact contact){
        contactRepository.save(contact);
    }
    public void addPhoneToContact(Contact contact,String phone){
    }
    public void deleteContacts(long id){
        List<Contact>contacts=contactRepository.findAll();
        for(Contact contact:contacts){
            if(contact.getUserId()==id)
                contactRepository.delete(contact);
        }
    }
    public List<Contact> getAllUserContacts(long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteriaQuery.from(Contact.class);
// Define the parameter id
        ParameterExpression<Long> paramId = criteriaBuilder.parameter(Long.class);
// Build the query
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("userId"), paramId));
// Execute the query
        TypedQuery<Contact> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(paramId, id);
        return typedQuery.getResultList();
    }
    public List<Contact> getAllUserContactsFiltered(long id,String email,String phone,String name){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> contactRoot = cq.from(Contact.class);

        Join<Contact, Email> emailJoin = contactRoot.join("emails");
        Join<Contact, Phone> phoneJoin = contactRoot.join("phones");

        Predicate predicate = cb.and(
                cb.equal(contactRoot.get("userId"), id),
                cb.or(
                        cb.like(contactRoot.get("name"),"%"+name+"%"),
                        cb.like(emailJoin.get("email"), "%"+email+"%"),
                        cb.like(phoneJoin.get("phone"), "%"+phone+"%")
                )
        );
        cq.select(contactRoot).where(predicate);
        System.out.println(name);
        return entityManager.createQuery(cq).getResultList();
    }

    public Contact getContact(long id){
        return contactRepository.findById(id).orElse(null);
    }
}
