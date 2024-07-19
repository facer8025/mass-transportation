package com.softka.massive_trasport.massive_transport_api.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;

public interface ITransactionRepository extends MongoRepository<Transaction, String> {

    Optional<Transaction> findByUserId(String userId);

    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

}
