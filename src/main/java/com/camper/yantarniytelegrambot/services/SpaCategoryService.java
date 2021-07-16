package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.repos.SpaServiceCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SpaCategoryService {
    private SpaServiceCategoryRepo spaServiceCategoryRepo;

    @Autowired
    public void setSpaServiceCategoryRepo(SpaServiceCategoryRepo spaServiceCategoryRepo) {
        this.spaServiceCategoryRepo = spaServiceCategoryRepo;
    }
}
