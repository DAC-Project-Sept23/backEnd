package com.app.dto;


import java.sql.Timestamp;


import com.app.entities.CompositKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingDto {

	private Long userId;
	private Long bookId;
	@JsonProperty(access = Access.READ_ONLY)
	private String firstName;
	@JsonProperty(access = Access.READ_ONLY)
	private String lastName;

	private String comment;
	private int rating;
	private Timestamp timestamp; 
}
