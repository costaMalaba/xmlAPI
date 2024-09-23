package com.cymstarlink.xmlAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.LinkedHashMap;

@Entity
@NamedStoredProcedureQuery(name = "ResponseCode.codeResponse",
        procedureName = "GET_USER_DETAILS",
        parameters = {
                @StoredProcedureParameter(name = "count", mode = ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "raw_response", mode = ParameterMode.OUT, type = String.class)
        })
@Table(name = "RESPONSE_CODE")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "CODE", unique = true, nullable = false, length = 10)
    private String code;

    @Column(name = "STATUS", nullable = false, length = 8)
    private String status;

    @Column(name = "DESCRIPTION", nullable = false, length = 3000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
