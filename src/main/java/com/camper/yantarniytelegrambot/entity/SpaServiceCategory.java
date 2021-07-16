package com.camper.yantarniytelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "spa_service_category")
public class SpaServiceCategory {
    private Integer categoryId;
    private String category;
    private String description;
    @JsonManagedReference
    private List<SpaService> services;
    @JsonBackReference
    private Location location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    public Integer getCategoryId() {
        return categoryId;
    }

    @Basic
    @Column(name = "category", nullable = false, length = 120)
    public String getCategory() {
        return category;
    }

    @Basic
    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    @OneToMany(mappedBy = "spaServiceCategory")
    public List<SpaService> getServices() {
        return services;
    }

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName = "loc_id")
    public Location getLocation() {
        return location;
    }

    public SpaServiceCategory(String category, String description, List<SpaService> services, Location location) {
        this.category = category;
        this.description = description;
        this.services = services;
        this.location = location;
    }
}
