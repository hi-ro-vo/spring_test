package com.example.springmvc.repositories;

import com.example.springmvc.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionariesRepository extends JpaRepository<DictionaryEntity, Long> {
}
