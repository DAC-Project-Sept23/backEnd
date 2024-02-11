package com.app.entities;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Entity
@NoArgsConstructor
@Getter
@Setter      
@ToString
@AllArgsConstructor
//@Table(name = "wishlist")
public class Wishlist {
	 @EmbeddedId
	    private WishlistId id;

}
