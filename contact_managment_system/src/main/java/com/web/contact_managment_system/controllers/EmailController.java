package com.web.contact_managment_system.controllers;

import com.web.contact_managment_system.models.Email;
import com.web.contact_managment_system.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/email")
public class EmailController {
    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/add")
    public String addToContact(@RequestParam("email") String emailsString, @RequestParam("id") long id){
        Email email;
        if(emailService.findByEmail(emailsString)!=null){
            return "redirect:http://localhost:8080/email/add?id="+id+"&error=true";
        }
        email=new Email(emailsString,id);
        emailService.save(email);
        return "redirect:http://localhost:8080/contact/all";
    }
    @GetMapping("/add")
    public String addEmail(@RequestParam("id") long id,Model model){
        model.addAttribute("id",id);
        return "add-email";
    }
    @GetMapping("/update")
    public String updateContact(@RequestParam("id")long id, Model model){
        model.addAttribute("id",id);
        return "update-email";
    }
    @PostMapping("/update")
    public String updateContact(@RequestParam("id") long id,@RequestParam("email") String oldemString){
        Email email=emailService.findById(id);
        if(emailService.findByEmail(oldemString)!=null){
            return "redirect:http://localhost:8080/email/update?id="+id+"&error=true";
        }
        email.setEmail(oldemString);
        emailService.save(email);
        return "redirect:http://localhost:8080/contact/all";
    }

    @GetMapping("/delete")
    public String deleteEmail(@RequestParam("id")long id){
        emailService.deleteEmail(id);
        return "redirect:http://localhost:8080/contact/all";
    }
}
