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
@Table(name="prices")
public class SpaPrice {
    private Integer priceId;
    private byte[] image;
    @JsonBackReference
    private Location location;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    public Integer getPriceId() {
        return priceId;
    }

    @Basic
    @Column(name="image", nullable = false)
    public byte[] getImage() {
        return image;
    }

    @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName = "loc_id")
    public Location getLocation() {
        return location;
    }

    public SpaPrice(byte[] image, Location location) {
        this.image = image;
        this.location = location;
    }
}
