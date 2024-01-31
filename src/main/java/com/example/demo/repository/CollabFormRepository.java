package com.example.demo.repository;

import com.example.demo.entity.CollabForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollabFormRepository extends JpaRepository<CollabForm, Long> {
}
