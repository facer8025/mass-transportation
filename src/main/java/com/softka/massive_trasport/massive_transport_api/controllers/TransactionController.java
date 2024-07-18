package com.softka.massive_trasport.massive_transport_api.controllers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.services.ITransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableRabbit
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService service;

    @Autowired
    private RabbitTemplate rabbit;

    @Autowired
    private Queue transactionQueue;

    @GetMapping("/saludo")
    public String saludo() {
        return "Hola!";
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody Transaction transaction) {
        Transaction transactionTmp = new Transaction();

        try {
            /** Se guarda la transacción en base */
            transactionTmp = service.save(transaction);

            /** Se publica la transacción en Rabbit */
            rabbit.convertAndSend(transactionQueue.getName(), transactionTmp);
        } catch (Exception e) {
            log.error("Exception ", e);
        }

        return ResponseEntity.ok("Transaction Received");
    }

}
