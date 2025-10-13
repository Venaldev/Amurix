package my.sebastien.amurix.ledger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sebastien.amurix.ledger.entity.Transaction;
import my.sebastien.amurix.ledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final LedgerService ledgerService;

    /**
     * Fetch one transaction by id (read-only)
     * Finds oour account or throws an exception
     */
    @Transactional(readOnly = true)
    public Transaction get(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found for: " + id));
    }
}
