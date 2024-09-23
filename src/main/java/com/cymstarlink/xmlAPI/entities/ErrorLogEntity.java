package com.cymstarlink.xmlAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "ERROR_LOGS")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "ERR_MESSAGE", nullable = false, columnDefinition = "TEXT")
    private String errMessage;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
