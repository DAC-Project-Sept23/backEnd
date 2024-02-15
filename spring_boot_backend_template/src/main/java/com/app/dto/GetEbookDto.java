package com.app.dto;
import java.util.Base64;
import java.util.Arrays;

import com.app.custom_exceptions.ResourceNotFoundException;
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
	private String filePath;

	//private String base64EpubContent;
    //private byte[] epubFileContent;
    private byte[] coverImageContent;

    public GetEbookDto(String title, Genre genre, String description, double price, byte[] epubFileContent, byte[] coverImageContent) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        //this.epubFileContent = epubFileContent;
        this.coverImageContent = coverImageContent;
    }
    public GetEbookDto(String title, Genre genre, String description, double price, String epubPath) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.filePath = epubPath;
    }
    public GetEbookDto(String path) {
		this.filePath = path;
	}

	// Fields for EPUB file and cover image
	//private String imagePath;
	//private String imagePath;


	public GetEbookDto(String title, Genre genre, String description, double price,
			String filePath,byte[] coverImageContent) {
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
		
		System.out.println(filePath);
		String[] parts = filePath.split("\\\\");
		System.out.println(Arrays.toString(parts));
		this.filePath=parts[5];
		
		this.coverImageContent = coverImageContent;
	}

}
