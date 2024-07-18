package com.softka.massive_trasport.massive_transport_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.repositories.ITransactionRepository;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository repository;

    @Override
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

}
