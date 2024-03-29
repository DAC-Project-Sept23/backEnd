package com.app.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.Length;

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
	@Column(name="is_approved")
	private boolean isApproved;
	@ManyToOne
	@JoinColumn(name="approved_by")
	private User approvedBy;
	private Timestamp timestamp;
	
}
