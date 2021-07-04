package com.camper.yantarniytelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@Table(name = "employees")
public class Employee {
    private Integer emplId;
    private byte[] image;
    private String description;
    @JsonBackReference
    private Location location;
    @JsonBackReference
    private EmployeeType employeeType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empl_id", nullable = false)
    public Integer getEmplId() {
        return emplId;
    }

    @Basic
    @Column(name = "image", nullable = true)
    public byte[] getImage() {
        return image;
    }

    @Basic
    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "loc_id")
    public Location getLocation() {
        return location;
    }

    @ManyToOne
    @JoinColumn(name = "empl_type_id", referencedColumnName = "type_id")
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public Employee(byte[] image, String description, Location location, EmployeeType employeeType) {
        this.image = image;
        this.description = description;
        this.location = location;
        this.employeeType = employeeType;
    }
}
