package com.example.springmvc;

import com.example.springmvc.entity.DictionaryEntity;
import com.example.springmvc.repositories.DictionariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dictionaries {
    @Autowired
    protected DictionariesRepository dictionariesRepository;

    public DictionaryEntity getDictionary(Long id) {
        return dictionariesRepository.getById(id);
    }

    public List<DictionaryEntity> getDictionaries(){
        return dictionariesRepository.findAll();
    }
}
