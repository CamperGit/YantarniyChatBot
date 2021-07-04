package com.camper.yantarniytelegrambot.services;

import com.camper.yantarniytelegrambot.entity.Location;
import com.camper.yantarniytelegrambot.entity.Sale;
import com.camper.yantarniytelegrambot.repos.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SaleService {
    private SaleRepo saleRepo;

    /**
     * retrieve an entity from the database if it exists or else put sale in database
     * @param sale an example entity to retrieve from the database or a candidate to add to the database
     * @return sale from database
     */
    public Sale putIfAbsent(Sale sale) {
        return saleRepo.findOne(Example.of(sale)).orElseGet(()-> saleRepo.save(sale));
    }

    @Transactional(readOnly = true)
    public List<Sale> findAllByLocation(Location location) {
        return saleRepo.findAllByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<Sale> findAll() {
        return saleRepo.findAll();
    }

    @Autowired
    public void setSaleRepo(SaleRepo saleRepo) {
        this.saleRepo = saleRepo;
    }
}
