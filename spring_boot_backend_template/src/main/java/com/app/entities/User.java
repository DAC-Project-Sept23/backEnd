package com.app.entities;

import java.time.LocalDate;
import java.util.HashSet;
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
@ToString
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
	@JsonProperty(access = Access.READ_ONLY)
	Long id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String password;
	private LocalDate dob;
	
	   @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "user_wishlist",
	            joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "book_id"))
	    private Set<Ebook> wishlist = new HashSet<>();
	   
	   public User(Long id,String firstName, String lastName, String email,Role role,String password,
			LocalDate dob)
	   {
		   this.id=id;
		   this.firstName=firstName;
		   this.lastName=lastName;
		   this.email=email;
		   this.role=role;
		   this.password=password;
		   this.dob=dob;
	   }
	   
	   public void AddBookToWishList(Ebook book)
	   {
		   wishlist.add(book);
	   }

	
}
