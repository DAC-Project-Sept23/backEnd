package com.app.entities;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
    private IsApproved isApproved;
	@Column(name="is_removed")
	private boolean isRemoved;
	@ManyToOne
	@JoinColumn(name="approved_by")
	private User approvedBy;
	private Timestamp timestamp;
	
	

	
	//(String, Genre, String, double, String, String) is undefined
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
        this.timestamp = new Timestamp(currentSeconds * 1000);
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
	
	}
	
	//(long, String, Genre, String, double, String, String)
	

	
}
