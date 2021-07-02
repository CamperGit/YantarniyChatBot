package com.camper.yantarniytelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "locations", schema = "yantarniytb")
public class Location {
    private Integer locId;
    private String title;
    @JsonManagedReference
    private List<Employee> employees;
    @JsonManagedReference
    private List<Sale> sales;

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

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    public List<Employee> getEmployees() {
        return employees;
    }

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY)
    public List<Sale> getSales() {
        return sales;
    }
}
