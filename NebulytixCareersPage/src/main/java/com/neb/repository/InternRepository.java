package com.neb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neb.entity.InternFormData;

public interface InternRepository extends JpaRepository<InternFormData, Integer> {

}
