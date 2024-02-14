package com.app.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.app.entities.RatingId;
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
//	private Long userId;
//	private Long bookId;
	private RatingId id;
	@JsonProperty(access = Access.READ_ONLY)
	private String firstName;
	@JsonProperty(access = Access.READ_ONLY)
	private String lastName;
	private String comment;
	@NotNull
	@Min(value = 0 ,message = "Rating must be greater than or equal to 0")
	@Max(value = 5 ,message = "Rating must be less than or equal to 5")
	private int rating;
}
