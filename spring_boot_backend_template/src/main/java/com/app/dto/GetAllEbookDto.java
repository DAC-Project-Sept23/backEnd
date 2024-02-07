package com.app.dto;

import com.app.entities.Genre;
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
public class GetAllEbookDto {

//private Long userId;
//private String firstName;
//private String lastName;
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String title;
	private Genre genre;
	private String description;
	private double price;

	// Fields for EPUB file and cover image

	

   
    private byte[] coverImageContent;

    public GetAllEbookDto(Long id, Genre genre, String description, double price, byte[] coverImageContent) {
       
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent = coverImageContent;
    }	

}
