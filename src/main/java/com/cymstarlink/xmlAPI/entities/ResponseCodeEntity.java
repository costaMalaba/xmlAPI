package com.cymstarlink.xmlAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RESPONSE_CODE")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "CODE", unique = true, nullable = false)
    private  String code;

    @Column(name = "STATUS", nullable = false, length = 8)
    private  String status;

    @Column(name = "DESCRIPTION", nullable = false, length = 3000)
    private String description;
}
