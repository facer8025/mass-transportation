package com.softka.massive_trasport.massive_transport_api.services;

import java.util.List;
import java.util.Optional;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;

public interface ITransactionService {

    Optional<Transaction> findByUserId(String userId);

    List<Transaction> findAll();

    void save(Transaction transaction);

}
