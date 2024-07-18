package com.softka.massive_trasport.massive_transport_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;

public interface ITransactionRepository extends MongoRepository<Transaction, String> {

}
