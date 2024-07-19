package com.softka.massive_trasport.massive_transport_api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Document(collection = "transactions")
@ToString
public class Transaction implements Serializable {

    @Id
    private String transactionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private String deviceNumber;

    private String userId;

    private String geoPosition;

    private Double amount;

}
