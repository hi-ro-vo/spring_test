package com.example.springmvc;

import com.example.springmvc.entity.EntryEntity;
import com.example.springmvc.entity.Message;
import com.example.springmvc.repositories.DictionariesRepository;
import com.example.springmvc.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class mvcController {
    @Autowired
    Dictionaries dictionariesService;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    protected DictionariesRepository dictionariesRepository;

    @GetMapping("/")
    public String index(ModelMap model){
        model.addAttribute("dictionaries",dictionariesService.getDictionaries());
        return "index";

    }

    @PostMapping("/edit/{id}")
    public String editEntity(@ModelAttribute Message entity){
        EntryEntity entryEntity = entryRepository.findById(entity.getId()).get();
        entryEntity.setKey(entity.getKey());
        entryEntity.setValue(entity.getValue());
        entryRepository.save(entryEntity);
        return "redirect:/";
    }


    @PostMapping("/add/{id}")
    public String addEntity(@ModelAttribute Message entity){
        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setKey(entity.getKey());
        entryEntity.setValue(entity.getValue());
        entryEntity.setDictionaryEntity(dictionariesService.getDictionary(entity.getId()));
        entryRepository.save(entryEntity);
        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id){
        entryRepository.deleteById(id);
        return "redirect:/";
    }
}
