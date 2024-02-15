package com.app.dto;

import java.sql.Timestamp;
import com.app.entities.Genre;
import com.app.entities.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetAllEbookDto {

    private String firstName;
	private String lastName;
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String title;
	private Genre genre;
	private String description;
	private double price;
	private Status status;
	private Long processedBy;
	private Timestamp processedOn;
	private String comment;
    private byte[] coverImageContent;
    private Timestamp addedOn;

    public GetAllEbookDto(String firstName,String lastName,Long id,String title,Genre genre, String description, double price, Status sts,Long processedBy,Timestamp processedOn,byte[] coverImageContent) {
      
    	this.id=id;
    	this.firstName=firstName;
       this.lastName=lastName;
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent = coverImageContent;
        this.status=sts;
        this.processedBy=processedBy;
        this.processedOn=processedOn;
    }
    
    //for rejected with comment
    public GetAllEbookDto(String firstName,String lastName,Long id,String title,Genre genre, String description, double price, Status sts,Long processedBy,Timestamp processedOn,byte[] coverImageContent, String comment, Timestamp addedOn) {
        
    	this.id=id;
    	this.firstName=firstName;
       this.lastName=lastName;
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent = coverImageContent;
        this.status=sts;
        this.processedBy=processedBy;
        this.processedOn=processedOn;
        this.comment = comment;
        this.addedOn = addedOn;
    }
    
    public GetAllEbookDto(String firstName,String lastName,Long id,String title,Genre genre, String description, double price, Status sts,byte[] coverImageContent, Timestamp addedOn) {
        
    	this.id=id;
    	this.firstName=firstName;
       this.lastName=lastName;
    	this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.coverImageContent=coverImageContent;
        this.status=sts;
        this.addedOn = addedOn;
    }	

}
