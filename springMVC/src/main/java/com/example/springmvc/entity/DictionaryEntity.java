package com.example.springmvc.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    String regex;

    @OneToMany(mappedBy = "dictionaryEntity", fetch = FetchType.EAGER)
    List<EntryEntity> entries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<EntryEntity> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryEntity> entries) {
        this.entries = entries;
    }
}
