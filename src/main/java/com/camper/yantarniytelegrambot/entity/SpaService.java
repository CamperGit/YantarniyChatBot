package com.camper.yantarniytelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name="spa_services")
public class SpaService {
    private Integer spaServId;
    private String name;
    private String price;
    private String description;
    @JsonBackReference
    private SpaServiceType spaServiceType;
    @JsonBackReference
    private Location location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spa_serv_id")
    public Integer getSpaServId() {
        return spaServId;
    }

    @Basic
    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    @Basic
    @Column(name="price", nullable = false)
    public String getPrice() {
        return price;
    }

    @Basic
    @Column(name="description")
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "category_id")
    public SpaServiceType getSpaServiceType() {
        return spaServiceType;
    }

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName = "loc_id")
    public Location getLocation() {
        return location;
    }

    public SpaService(String name, String price, String description, SpaServiceType spaServiceType, Location location) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.spaServiceType = spaServiceType;
        this.location = location;
    }
}
