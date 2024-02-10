package com.app.entities;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "ratings")
public class Rating {
    
   

	@EmbeddedId
    private RatingId id;
    
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToOne
    @JoinColumn(name = "ebook_id", insertable = false, updatable = false)
    private Ebook ebook;
    

	private String comment;
    
    private int rating;
    
    private Timestamp timestamp;

    public Rating(RatingId id, User user, Ebook ebook, String comment, int rating) {
		super();
		this.id = id;
		this.user = user;
		this.ebook = ebook;
		this.comment = comment;
		this.rating = rating;
		// Get the current time in seconds precision
        long currentSeconds = Instant.now().getEpochSecond();

        // Create a Timestamp without milliseconds
        this.timestamp = new Timestamp(currentSeconds * 1000);
	}
}
