package com.example.cmart.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cmart.app.entity.RatingEntity;
import com.example.cmart.app.repository.RatingRepository;
import com.example.cmart.app.service.impl.ImplRatingService;

@Service
public class RatingService implements ImplRatingService{

	@Autowired
	private RatingRepository repo;
	
	@Override
	public RatingEntity save(RatingEntity rating) {
		return repo.save(rating);
	}
}
