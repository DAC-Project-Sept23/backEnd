package com.app.repositories;

import java.util.List;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Rating;
import com.app.entities.RatingId;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
	List<Rating> findByEbookId(Long id);
}
