package com.softka.massive_trasport.massive_transport_api.services;

import org.springframework.stereotype.Service;

import com.softka.massive_trasport.massive_transport_api.entities.DailySummary;
import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.repositories.IDailySummaryRepository;
import com.softka.massive_trasport.massive_transport_api.repositories.ITransactionRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Service
public class DailySummaryService implements IDailySummaryService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IDailySummaryRepository dailySummaryRepository;

    @Scheduled(cron = "0 * * * * *")
    public void generateDailySumary() {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);

        log.info("Fecha consolidado: " + yesterday);

        var transactions = transactionRepository.findAll();

        if (!transactions.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            double totalAmount = transactions.stream()
                                         .filter(transaction -> transaction.getTimestamp().format(formatter).equals(yesterday.format(formatter)))
                                         .mapToDouble(Transaction::getAmount)
                                         .sum();

            DailySummary summary = new DailySummary();
            summary.setDate(yesterday);
            summary.setTotalAmount(totalAmount);

            dailySummaryRepository.save(summary);
        } else {
            log.info("No se encontraron registros!");
        }
    }

}
