package com.neb.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "careerform")
@EntityListeners(AuditingEntityListener.class)  // enable auditing
public class InternFormData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carre1")
    @SequenceGenerator(name = "carre1", sequenceName = "seq1", initialValue = 1, allocationSize = 1)
    private Integer cid;

    @Column(name = "cname", length = 50, nullable = false)
    private String cname;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "phoneno", nullable = false)
    private Long phoneno;

    @Column(name = "domain", length = 50, nullable = false)
    private String domain;
    @Column(name = "resume_path", length = 255)
    private String resumePath;

    // Audit fields
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}

