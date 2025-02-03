package com.eventRegistration.eventRegistrationSystem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eventRegistration.eventRegistrationSystem.model.Visitor;
import com.eventRegistration.eventRegistrationSystem.repository.VisitorRepository;
import com.eventRegistration.eventRegistrationSystem.service.VisitorService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.qrcode.Mode;

@RestController
@RequestMapping("/api/eventRegistrationSystem")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Allow frontend origin
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("visitor", new Visitor());
        return "registration";
    }

    // @PostMapping("/register")
    // public String registerVisitor(Visitor visitor, @RequestParam("photo") MultipartFile photo, Model model) {
    //     try {
    //         visitorService.registerVisitor(visitor, photo);
    //         model.addAttribute("visitor", visitor);
    //         return "badge";
    //     } catch (Exception e) {
    //         model.addAttribute("error", "Registration failed. Please try again");
    //         return "registration";
    //     }
    // }

    @PostMapping("/register")
    public ResponseEntity<String> regisiterVisitor(@RequestParam("fullName") String fullName, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("photo") MultipartFile photo) {
        try {
            String response = visitorService.registerVisitor(fullName, email, phone, photo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/downloadBadge/{id}")
    public void downloadBadge(@PathVariable int id, HttpServletResponse response) throws IOException, DocumentException {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new RuntimeException("Visitor not found"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Desposition", "attachment; filename=badge.pdf");
        visitorService.generateBadge(visitor, response.getOutputStream());
    }
    
}
