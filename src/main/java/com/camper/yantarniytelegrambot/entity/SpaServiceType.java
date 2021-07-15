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
@Table(name = "spa_service_type")
public class SpaServiceType {
    private Integer spaServTypeId;
    private String type;
    private String title;
    @JsonManagedReference
    private List<SpaService> services;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spa_serv_type_id", nullable = false)
    public Integer getSpaServTypeId() {
        return spaServTypeId;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return type;
    }

    @Basic
    @Column(name = "title", length = 50)
    public String getTitle() {
        return title;
    }

    @OneToMany(mappedBy = "spaServiceType",fetch = FetchType.LAZY)
    public List<SpaService> getServices() {
        return services;
    }
}
