package com.camper.yantarniytelegrambot.entity;

import com.camper.yantarniytelegrambot.enums.ScheduleType;
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
@Table(name = "schedules", schema = "yantarniytb")
public class Schedule {
    private Integer schedId;
    private byte[] image;
    private String description;
    private ScheduleType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sched_id", nullable = false)
    public Integer getSchedId() {
        return schedId;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public ScheduleType getType() {
        return type;
    }
}
