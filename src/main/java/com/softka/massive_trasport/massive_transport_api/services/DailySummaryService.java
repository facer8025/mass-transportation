package com.softka.massive_trasport.massive_transport_api.services;

import org.springframework.stereotype.Service;

import com.softka.massive_trasport.massive_transport_api.entities.DailySummary;
import com.softka.massive_trasport.massive_transport_api.entities.Transaction;
import com.softka.massive_trasport.massive_transport_api.repositories.IDailySummaryRepository;
import com.softka.massive_trasport.massive_transport_api.repositories.ITransactionRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

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

        List<Transaction> transactions = transactionRepository.findAll();

        double totalAmount = transactions.stream()
                                         .filter(transaction -> transaction.getTimestamp().toLocalDate().equals(yesterday))
                                         .mapToDouble(Transaction::getAmount)
                                         .sum();

        DailySummary summary = new DailySummary();
        summary.setDate(yesterday);
        summary.setTotalAmount(totalAmount);

        dailySummaryRepository.save(summary);
    }

}
