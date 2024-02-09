package com.app.entities;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "rejection_id"))
public class Rejected extends BaseEntity {
	@Column(name = "file_path")
	private String bookPath;
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private User admin;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	private String comment;
	private Timestamp timestamp;

}
