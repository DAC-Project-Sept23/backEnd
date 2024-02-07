package com.app.dto;

import org.springframework.web.multipart.MultipartFile;

import com.app.entities.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class EbookDto {

private Long userId;
	private String title;
	private Genre genre;
	private String description;
	private double price;

	// Fields for EPUB file and cover image

	private MultipartFile epubFile;
	
	private MultipartFile coverImage;
	@JsonIgnore
	private byte[] epubFileContent;
	@JsonIgnore
	private byte[] coverImageContent;


	public EbookDto(String title, Genre genre, String description, double price) {

		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
	}

	public EbookDto(String title, Genre genre, String description, double price, MultipartFile epubFile,
			MultipartFile coverImage) {
		super();
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
		this.epubFile = epubFile;
		this.coverImage = coverImage;
	}

	public EbookDto(String title, Genre genre, String description, double price, byte[] epubFileContent, byte[] coverImageContent) {
	    this.title = title;
	    this.genre = genre;
	    this.description = description;
	    this.price = price;
	    this.epubFileContent = epubFileContent;
	    this.coverImageContent = coverImageContent;
	}

	

}