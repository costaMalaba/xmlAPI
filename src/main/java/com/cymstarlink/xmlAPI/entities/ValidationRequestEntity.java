package com.cymstarlink.xmlAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VALIDATION REQUESTS")
@Data
@ToString
public class ValidationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @Column(name = "control_number", nullable = false)
    private Long controlNumber;

    @Column(name = "raw_request")
    private String rawRequest;

    @OneToOne(
            mappedBy = "validationRequestEntity",
            cascade = CascadeType.ALL
    )
    private ValidationResponseEntity validationResponseEntity;
}
