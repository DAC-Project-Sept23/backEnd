package com.app.dto;

import java.util.Arrays;

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
public class GetEbookDto {
	@JsonIgnore
	private Long userId;
	private String title;
	private Genre genre;
	private String description;
	private double price;

	// Fields for EPUB file and cover image
	//private String imagePath;
	private String filePath;

	// private byte[] epubFileContent;
	 private byte[] coverImageContent;

	public GetEbookDto(String title, Genre genre, String description, double price,
			String filePath,byte[] coverImageContent) {
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
		System.out.println(filePath);
		String[] parts = filePath.split("\\\\");
		System.out.println(Arrays.toString(parts));
		this.filePath=parts[1];
		//this.imagePath=imagePath;
		// this.epubFileContent = epubFileContent;
		this.coverImageContent = coverImageContent;
	}

}
