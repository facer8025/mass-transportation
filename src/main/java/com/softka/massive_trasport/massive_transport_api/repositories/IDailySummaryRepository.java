package com.softka.massive_trasport.massive_transport_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.softka.massive_trasport.massive_transport_api.entities.DailySummary;

public interface IDailySummaryRepository extends MongoRepository<DailySummary, String> {

}
