package com.app.dto;

import com.app.entities.RatingId;

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
	private String comment;
	private int rating;
}
