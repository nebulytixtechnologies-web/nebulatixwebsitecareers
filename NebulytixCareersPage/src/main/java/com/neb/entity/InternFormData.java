package com.neb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="careerform")
public class InternFormData {
	
	@SequenceGenerator(name="carre1",sequenceName = "seq1",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "seq1",strategy =GenerationType.SEQUENCE )
	@Id
	private Integer cId;
	@Column(length = 20)
	private String cName;
	@Column(length = 20)
	private String email;
    private Long phoneNo;
    private String resumePath;
    
    
}
