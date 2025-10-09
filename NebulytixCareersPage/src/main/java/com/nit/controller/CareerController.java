package com.nit.controller;

import com.nit.entity.CareerApplication;
import com.nit.repo.CareerRepository;
import com.nit.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/careers")
public class CareerController {

    @Autowired
    private CareerRepository careerRepository;

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
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            // ✅ Validate PDF file format
            String originalFileName = resumeFile.getOriginalFilename();
            String contentType = resumeFile.getContentType();

            if (originalFileName == null ||
                (!originalFileName.toLowerCase().endsWith(".pdf")) ||
                (contentType != null && !contentType.equalsIgnoreCase("application/pdf"))) {

                response.put("status", "error");
                response.put("message", "Invalid file format! Only PDF resumes are accepted.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // ✅ Create uploads directory if not exists
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // ✅ Generate safe, unique filename
            String safeFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            String fileName = UUID.randomUUID() + "_" + safeFileName;
            File dest = new File(dir, fileName);
            resumeFile.transferTo(dest);

            // ✅ Save to DB
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

            CareerApplication savedApp = careerRepository.save(app);

            // ✅ Send confirmation email
            String subject = "Application Received - " + role;
            String text = "Hello " + firstName + ",\n\n" +
                    "Thank you for applying for the " + role + " position at our company.\n\n" +
                    "Here are your application details:\n" +
                    "---------------------------------\n" +
                    "Name: " + firstName + " " + lastName + "\n" +
                    "Email: " + email + "\n" +
                    "Phone: " + phone + "\n" +
                    "Qualification: " + qualification + "\n" +
                    "Passout Year: " + passoutYear + "\n\n" +
                    "We will review your application and contact you soon.\n\n" +
                    "Best regards,\n" +
                    "HR Team";

            emailService.sendApplicationMail(email, subject, text);

            // ✅ Success response
            response.put("status", "success");
            response.put("message", "Application submitted successfully! Check your email for confirmation.");
            response.put("id", savedApp.getId());
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("status", "error");
            response.put("message", "File upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", "Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }//here the end of program
    }
}
