package com.camper.yantarniytelegrambot.entity;

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
@Table(name = "employees_types")
public class EmployeeType {
    private Integer typeId;
    private String type;
    private String title;
    @JsonManagedReference
    private List<Employee> employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    public Integer getTypeId() {
        return typeId;
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

    @OneToMany(mappedBy = "employeeType",fetch = FetchType.LAZY)
    public List<Employee> getEmployees() {
        return employees;
    }
}
