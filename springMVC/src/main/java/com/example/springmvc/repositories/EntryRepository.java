package com.example.springmvc.repositories;

import com.example.springmvc.entity.EntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<EntryEntity, Long> {
}
