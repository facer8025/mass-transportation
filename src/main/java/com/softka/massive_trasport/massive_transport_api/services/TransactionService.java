package com.softka.massive_trasport.massive_transport_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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

    private final List<Transaction> transactionBuffer = new CopyOnWriteArrayList<>();

    @Override
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @Async
    @Override
    @Transactional
    public void save(Transaction transaction) {

        transactionBuffer.add(transaction);

    }

    @Scheduled(fixedRate = 10000)
    public void processBatch() {
        
        if (!transactionBuffer.isEmpty()) {
            /** Se crea una copia de la lista transactionsBuffer */
            List<Transaction> transactionsSave = new ArrayList<>(transactionBuffer);
            /** Se limpia transactionsBuffer para que pueda ser llanda de nuevo */
            transactionBuffer.clear();
            /** Guardamos las transacciones */
            repository.saveAll(transactionsSave);
            /** Publicamos las transacciones */
            transactionsSave.forEach(transaction -> {
                rabbit.convertAndSend(transactionQueue.getName(), transaction);
            });
        }

    }

}
