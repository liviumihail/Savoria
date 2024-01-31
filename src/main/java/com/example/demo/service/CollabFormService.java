package com.example.demo.service;

import com.example.demo.dto.CollabFormDto;
import com.example.demo.entity.CollabForm;
import com.example.demo.repository.CollabFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollabFormService {

    @Autowired
    CollabFormRepository collabFormRepository;

    public void save(CollabFormDto collabFormDto) {
        CollabForm collabForm = new CollabForm();
        collabForm.setName(collabFormDto.getName());
        collabForm.setDescription(collabFormDto.getDescription());
        collabForm.setEmail(collabFormDto.getEmail());
        collabForm.setPhone(collabFormDto.getPhone());
        collabFormRepository.save(collabForm);
    }
}
