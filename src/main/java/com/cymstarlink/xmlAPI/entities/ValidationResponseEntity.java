package com.cymstarlink.xmlAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "VALIDATION_RESPONSES")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ValidationResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @Column(name = "control_number", nullable = false)
    private Long controlNumber;

    @Column(name = "raw_response")
    private String rawResponse;

    @Column(name = "status_message")
    private String statusMessage;

    @OneToOne
    @JoinColumn(
            name = "validation_request_id"
    )
    private ValidationRequestEntity validationRequestEntity;
}
