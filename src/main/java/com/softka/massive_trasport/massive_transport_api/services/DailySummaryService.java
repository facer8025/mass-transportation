package com.softka.massive_trasport.massive_transport_api.services;

import org.springframework.stereotype.Service;

import com.softka.massive_trasport.massive_transport_api.entities.DailySummary;
import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.repositories.IDailySummaryRepository;
import com.softka.massive_trasport.massive_transport_api.repositories.ITransactionRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Service
public class DailySummaryService implements IDailySummaryService {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IDailySummaryRepository dailySummaryRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void generateDailySumary() {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        LocalDateTime endOfYesterday = yesterday.atTime(LocalTime.MAX);

        log.info("Fecha consolidado: " + yesterday);

        var transactions = transactionRepository.findByTimestampBetween(startOfYesterday, endOfYesterday);

        if (!transactions.isEmpty()) {
            double totalAmount = transactions.stream()
                                         .filter(transaction -> transaction.getTimestamp().toLocalDate().equals(yesterday))
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
