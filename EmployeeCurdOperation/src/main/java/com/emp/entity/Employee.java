package com.emp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @NotBlank(message = "first name cannot be blank")
    @NotEmpty(message = "first name cannot be blank")
	@Size(max = 100)
	private String firstname;
    
    @NotBlank(message = "lastname cannot be blank")
	@Size(max = 100)
	private String lastname;
    
    @Email
    @NotBlank(message = "email id not blank")
    @Size(max = 100)
	private String email;
    
    @Min(18)
	private int age;
}
