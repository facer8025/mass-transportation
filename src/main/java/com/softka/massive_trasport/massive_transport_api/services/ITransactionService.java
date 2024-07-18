package com.softka.massive_trasport.massive_transport_api.services;

import java.util.List;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;

public interface ITransactionService {

    List<Transaction> findAll();

    Transaction save(Transaction transaction);

}
