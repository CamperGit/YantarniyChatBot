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
@Table(name = "employees", schema = "yantarniytb")
public class Employee {
    private Integer emplId;
    private byte[] image;
    private String description;
    private String firstname;
    private String middlename;
    private String lastname;
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
    @Column(name = "description", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 25)
    public String getFirstname() {
        return firstname;
    }

    @Basic
    @Column(name = "middlename", nullable = false, length = 25)
    public String getMiddlename() {
        return middlename;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 25)
    public String getLastname() {
        return lastname;
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
}
