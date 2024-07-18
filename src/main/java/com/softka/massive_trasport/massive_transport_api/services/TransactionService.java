package com.softka.massive_trasport.massive_transport_api.services;

import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.repositories.ITransactionRepository;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository repository;

    @Autowired
    private RabbitTemplate rabbit;

    @Autowired
    private Queue transactionQueue;

    @Override
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @Async
    @Override
    @Transactional
    public void save(Transaction transaction) {

        Transaction transactionTmp = new Transaction();

        transactionTmp = repository.save(transaction);
        /** Se publica la transacción en Rabbit */
        rabbit.convertAndSend(transactionQueue.getName(), transactionTmp);
    }

}
