package com.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
	
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String password;
	private LocalDate dob;
	
	
}
