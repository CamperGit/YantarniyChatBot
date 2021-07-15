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
    @JsonBackReference
    private SpaServiceType spaServiceType;

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

    @ManyToOne
    @JoinColumn(name="service_type_id", referencedColumnName = "spa_serv_type_id")
    public SpaServiceType getSpaServiceType() {
        return spaServiceType;
    }

    public SpaService(String name, String price, SpaServiceType spaServiceType) {
        this.name = name;
        this.price = price;
        this.spaServiceType = spaServiceType;
    }
}
