
package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingId implements Serializable {

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "ebook_id")
	private Long ebookId;
}
