package com.app.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "ebooks")
public class Ebook extends BaseEntity {
	@JsonProperty(access = Access.READ_ONLY)
	Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")   
	private User user;
	
	private String title;
	@Enumerated(EnumType.STRING)
	private Genre genre;
	private String description;
	private double price;
	@Column(name = "file_path")
	private String filePath;
	@Column(name = "images_path")
	private String imagePath;
	@Column(name = "added_on")
	private Timestamp addedOn;
	@Column
    private boolean isRemoved;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status",length=20)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name="processed_by")
	private User processedBy;
	
	//Approved Time Stamp
	
	@Column(name = "processed_on" )
	private Timestamp processedOn;
	
	public Ebook( String title, Genre genre, String description, double price, String filePath,
			String imagePath) {
		
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
		this.filePath = filePath;
		this.imagePath = imagePath;
		// Get the current time in seconds precision
        long currentSeconds = Instant.now().getEpochSecond();

        // Create a Timestamp without milliseconds
        this.addedOn = new Timestamp(currentSeconds * 1000);
	}
	public Ebook(User user, String title, Genre genre, String description, double price, String filePath,
			String imagePath) {
		super();
		this.user = user;
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.price = price;
		this.filePath = filePath;
		this.imagePath = imagePath;
		this.status=Status.PENDING;
	
	}
	
}
