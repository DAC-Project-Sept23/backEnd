package com.app.dto;

import java.sql.Timestamp;

import com.app.entities.Genre;
import com.app.entities.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class GetAllEbookDto {

    //private Long userId;
    private String firstName;
	private String lastName;
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String title;
	private Genre genre;
	private String description;
	//private String filePath;
	private double price;
	private Status status;
	private Long approvedBy;
	private Timestamp approvedOn;
	
	
	// Fields for EPUB file and cover image

	

  // private String imagePath;
    private byte[] coverImageContent;

    public GetAllEbookDto(String firstName,String lastName,Long id,String title,Genre genre, String description, double price, Status sts,Long approvedBy,Timestamp approvedOn,byte[] coverImageContent) {
      
    	this.id=id;
    	this.firstName=firstName;
       this.lastName=lastName;
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent = coverImageContent;
        this.status=sts;
        this.approvedBy=approvedBy;
        this.approvedOn=approvedOn;
    }	
    
    public GetAllEbookDto(String firstName,String lastName,Long id,String title,Genre genre, String description, double price, Status sts,byte[] coverImageContent) {
        
    	this.id=id;
    	this.firstName=firstName;
       this.lastName=lastName;
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent=coverImageContent;
        this.status=sts;
        
    }	

}
