package com.example.springmvc.entity;

import javax.persistence.*;

@Entity
public class EntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String key;
    String value;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    DictionaryEntity dictionaryEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DictionaryEntity getDictionaryEntity() {
        return dictionaryEntity;
    }

    public void setDictionaryEntity(DictionaryEntity dictionaryEntity) {
        this.dictionaryEntity = dictionaryEntity;
    }
}
