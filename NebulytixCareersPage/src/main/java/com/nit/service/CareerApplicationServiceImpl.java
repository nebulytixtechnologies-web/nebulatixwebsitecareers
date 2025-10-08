package com.nit.service;

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
		CareerApplication save = repo.save(app);
		
		return save;
	}

}
