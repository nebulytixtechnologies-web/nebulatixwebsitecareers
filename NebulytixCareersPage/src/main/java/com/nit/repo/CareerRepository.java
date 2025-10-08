package com.nit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nit.entity.CareerApplication;

public interface CareerRepository extends JpaRepository<CareerApplication, Long>
{
	Optional<CareerApplication> findByEmail(String email);
}

