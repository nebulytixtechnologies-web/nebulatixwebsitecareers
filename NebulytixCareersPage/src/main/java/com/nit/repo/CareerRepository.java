package com.nit.repo;

import com.nit.entity.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CareerRepository extends JpaRepository<CareerApplication, Long> {}

