package com.softka.massive_trasport.massive_transport_api.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection = "daily_summaries")
public class DailySummary {

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate date;

    private double totalAmount;

}
