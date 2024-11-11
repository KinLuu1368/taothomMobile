package com.lab08.main.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lab08.main.service.EmailService;

@Controller
public class ContactRestController {

    @Autowired
    private EmailService emailService;

 
    @GetMapping("/contact")
    public String showContactPage() {
        return "security/contact"; 
    }


    @PostMapping("/contact")
    public String sendContactMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message) {
        
        String subject = "New Contact Form Submission";
        String body = String.format("Name: %s\nEmail: %s\n\nMessage:\n%s", name, email, message);

        emailService.sendEmail("toai0706@gmail.com", subject, body);

        return "redirect:/contact?success=true";  
    }
}
