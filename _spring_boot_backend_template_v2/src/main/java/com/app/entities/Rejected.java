package com.app.entities;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rejected_books")
@Getter
@Setter  
@NoArgsConstructor

@AttributeOverride(name = "id", column = @Column(name = "rejection_id"))
public class Rejected extends BaseEntity {
	@Column(name = "file_path")
	private String bookPath;
	@Column(name = "file_title")
	private String title;
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private User admin;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	private String comment;
	private Timestamp timestamp;
	@ManyToOne(fetch = FetchType.LAZY)
	private Ebook ebook;
	public Rejected(String title,String bookPath, User admin, User author, String comment, Ebook ebook) {
		super();
		this.title=title;
		this.bookPath = bookPath;
		this.admin = admin;
		this.author = author;
		this.comment = comment;
        long currentSeconds = Instant.now().getEpochSecond();
        this.timestamp = new Timestamp(currentSeconds * 1000);
        this.ebook = ebook;
	}
	
	
	

}
