package com.web.contact_managment_system.controllers;

import com.web.contact_managment_system.models.Contact;
import com.web.contact_managment_system.models.User;
import com.web.contact_managment_system.services.ContactService;
import com.web.contact_managment_system.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequestMapping("/contact")
@Controller
public class ContactController {
    private ContactService contactService;
    private UserService userService;

    public ContactController(ContactService contactService,UserService userService) {
        this.contactService = contactService;
        this.userService=userService;
    }
    @GetMapping("/all")
    public String getAllContacts(Authentication authentication, Model model,@RequestParam("email")Optional<String> email,@RequestParam("phone")Optional<String> phone,@RequestParam("name")Optional<String> name){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        User user=userService.findByUsername(userId).orElse(null);
        List<Contact>contacts;
        if(email.isEmpty() && phone.isEmpty())
            contacts=contactService.getAllUserContacts(user.getId());
        else
            contacts=contactService.getAllUserContactsFiltered(user.getId(),email.get(),phone.get(),name.get());
        model.addAttribute("contacts",contacts);
        return "contacts";
    }
    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") long id, Model model){
        Contact contact=contactService.getContact(id);
        model.addAttribute("emails",contact.getEmails());
        model.addAttribute("id",id);
        model.addAttribute("phones",contact.getPhones());
        return "show-contact";
    }
    @GetMapping("/delete")
    public String deleteContact(@RequestParam("id")long id){
        contactService.deleteContact(id);
        return "redirect:http://localhost:8080/contact/all";
    }
    @GetMapping("/update")
    public String updateContact(@RequestParam("id")long id,Model model){
        model.addAttribute("id",id);
        return "update-contact";
    }
    @PostMapping("/update")
    public String updateContact(@RequestParam("id")long id,@RequestParam("name") String name,@RequestParam("location") String location){
        Contact contact=contactService.getContact(id);
        contact.setName(name);
        contact.setLocation(location);
        contactService.saveContact(contact);
        return "redirect:http://localhost:8080/contact/all";
    }
    @GetMapping("/create")
    public String create(){
        return "create-contact";
    }
    @PostMapping("/create")
    public String create(@RequestParam("name")String name,@RequestParam("location")String location,Authentication authentication){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        long userId=userService.findByUsername(userDetails.getUsername()).orElse(null).getId();
        Contact contact=new Contact(name,location,userId);
        contactService.saveContact(contact);
        return "redirect:http://localhost:8080/contact/all";
    }
    @GetMapping("/reset")
    public String resetContacts(Authentication authentication){
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        long userId=userService.findByUsername(userDetails.getUsername()).orElse(null).getId();
        contactService.deleteContacts(userId);
        return "redirect:http://localhost:8080/contact/all";
    }
}
