package com.programming.techie.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "failure_record_message")
public class FailureRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "key_value")
    private Integer keyValue;

    @Column(name = "error_record")
    private String errorRecord;

    @Column(name = "partition_record")
    private Integer partition;

    @Column(name = "offset_value")
    private Long offsetValue;

    @Column(name = "exception")
    private String exception;

    @Column(name = "status")
    private String status;

}
