package com.nit.controller;





import com.nit.entity.CareerApplication;
import com.nit.service.EmailService;
import com.nit.service.ICareerApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/careers")
public class CareerController {
   
   @Autowired 
   private ICareerApplicationService service;
    @Autowired
    private EmailService emailService;
    

    @PostMapping("/apply")
    public ResponseEntity<?> applyForCareer(
            @RequestParam("role") String role,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("qualification") String qualification,
            @RequestParam("passoutYear") int passoutYear,
            @RequestParam("resume") MultipartFile resumeFile
    ) throws IOException {

        // Save resume to uploads folder
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
        File dest = new File(dir, fileName);
        resumeFile.transferTo(dest);

        // Save application to DB
        CareerApplication app = new CareerApplication();
        app.setRole(role);
        app.setFirstName(firstName);
        app.setLastName(lastName);
        app.setEmail(email);
        app.setPhone(phone);
        app.setQualification(qualification);
        app.setPassoutYear(passoutYear);
        app.setResumeFileName(fileName);
        app.setAppliedAt(Instant.now());

       CareerApplication saved = service.insert(app);
        
        
     // Send confirmation email to applicant
        String subject = "Application Received - " + role;
        String text = "Hello " + firstName + ",\n\n" +
                "Thank you for applying for the " + role + " position at our company.\n" +
                "Here are the details you submitted:\n\n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Qualification: " + qualification + "\n" +
                "Passout Year: " + passoutYear + "\n\n" +
                "We will review your application and get back to you soon.\n\n" +
                "Best regards,\nCompany HR Team";

        emailService.sendApplicationMail(email, subject, text);

        return ResponseEntity.ok(saved);
    }
}
