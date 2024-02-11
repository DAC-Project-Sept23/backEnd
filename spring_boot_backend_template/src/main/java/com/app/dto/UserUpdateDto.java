package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserUpdateDto {
	//@JsonProperty(access = Access.READ_ONLY)
	@JsonIgnore
	private Long Id;
	
	private String firstName;
	private String lastName;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;


}