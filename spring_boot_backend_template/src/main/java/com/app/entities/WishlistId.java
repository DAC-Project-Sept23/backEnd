package com.app.entities;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WishlistId implements Serializable{
	   @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Ebook book;

}
