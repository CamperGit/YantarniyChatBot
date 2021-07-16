package com.camper.yantarniytelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "locations")
public class Location {
    private Integer locId;
    private String title;
    @JsonManagedReference
    private List<Employee> employees;
    @JsonManagedReference
    private List<Sale> sales;
    @JsonManagedReference
    private List<SpaPrice> services;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loc_id", nullable = false)
    public Integer getLocId() {
        return locId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    public List<Employee> getEmployees() {
        return employees;
    }

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    public List<Sale> getSales() {
        return sales;
    }

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    public List<SpaPrice> getServices() {
        return services;
    }

    public Location(String title, List<Employee> employees, List<Sale> sales, List<SpaPrice> services) {
        this.title = title;
        this.employees = employees;
        this.sales = sales;
        this.services = services;
    }
}
