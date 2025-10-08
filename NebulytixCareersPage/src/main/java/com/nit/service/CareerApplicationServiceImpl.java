package com.nit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nit.entity.CareerApplication;
import com.nit.repo.CareerRepository;

@Service
public class CareerApplicationServiceImpl implements ICareerApplicationService
{
	@Autowired
	private CareerRepository repo;
	

	@Override
	public CareerApplication insert(CareerApplication app) 
	{
		
		 Optional<CareerApplication> existing = repo.findByEmail(app.getEmail());

		    if (existing.isPresent()) 
		    {
		        throw new IllegalArgumentException("Email already exists");
		    }
          
		    return repo.save(app);
		
	
	}

}
