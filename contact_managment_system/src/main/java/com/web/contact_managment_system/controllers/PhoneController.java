package com.web.contact_managment_system.controllers;
import com.web.contact_managment_system.models.Phone;
import com.web.contact_managment_system.services.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/phone")
public class PhoneController {
        PhoneService phoneService;

        public PhoneController(PhoneService phoneService) {
            this.phoneService = phoneService;
        }
        @PostMapping("/add")
        public String addToContact(@RequestParam("phone")String phoneString, @RequestParam("id") long id){
            Phone phone=phoneService.findByPhone(phoneString);
            if(phone!=null){
                return "redirect:http://localhost:8080/phone/add?id="+id+"&error=true";
            }
            phone=new Phone(phoneString,id);
            phoneService.save(phone);
            return "redirect:http://localhost:8080/contact/all";
        }
        @GetMapping("/add")
        public String addPhone(@RequestParam("id")long id,Model model){
            model.addAttribute("id",id);
            return "add-phone";
        }
        @GetMapping("/update")
        public String updatePhone(@RequestParam("id")long id, Model model){
            model.addAttribute("id",id);
            return "update-phone";
        }
        @PostMapping("/update")
        public String updateContact(@RequestParam("phone") String emString,@RequestParam("id") long id){
            Phone phone=phoneService.findById(id);
            if(phoneService.findByPhone(emString)!=null){
                return "redirect:http://localhost:8080/phone/update?error=true";
            }
            phone.setPhone(emString);
            phoneService.save(phone);
            return "redirect:http://localhost:8080/contact/all";
        }

        @GetMapping("/delete")
        public String deleteEmail(@RequestParam("phone")String phone){
            phoneService.delete(phone);
            return "http://localhost:8080/contact/all";
        }
    }

