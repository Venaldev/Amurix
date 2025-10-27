package my.sebastien.amurix.ledger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sebastien.amurix.ledger.entity.Transaction;
import my.sebastien.amurix.ledger.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final LedgerService ledgerService;

    @Transactional
    public Transaction createAndPost(Long fromAccId,
                                     Long toAccId,
                                     BigDecimal amount,
                                     String currency,
                                     String idemptKey) {
        Optional<Transaction> existing = transactionRepository.findByIdempKey(idemptKey);
        if (existing.isPresent()) {
            log.info("Idempotent replay: key={}, returning transaction id={}", idemptKey, existing.get().getId());
            return existing.get();
        }

        requireDifferentAcc(fromAccId, toAccId);
        requirePositive(amount);

        // TODO: assert tests

        Transaction transaction = Transaction.builder()
                .fromAccountId(fromAccId)
                .toAccountId(toAccId)
                .amount(amount)
                .currency(currency)
                .status(Transaction.TransactStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            ledgerService.postTransfer(fromAccId, toAccId, amount, currency, transaction.getId());
            transaction.setStatus(Transaction.TransactStatus.POSTED);
            transaction = transactionRepository.save(transaction);

            log.info("Transaction POSTED id={} from={} to={} amount{} currency={}", transaction.getId(), fromAccId, toAccId, amount, currency);

            return transaction;
            // TODO: Custom exception handling
        } catch (Exception ex) {
            log.warn("Transaction REJECTED id={} reason={}", transaction.getId(), ex.getMessage());

            transaction.setStatus(Transaction.TransactStatus.REJECTED);
            transactionRepository.save(transaction);

            // Re-throw this so the transactional rolls back the unit
            throw ex;
        }
    }

    /**
     * Fetch one transaction by id (read-only)
     * Finds our account or throws an exception
     */
    @Transactional(readOnly = true)
    public Transaction get(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found for: " + id));
    }


    /*
     * Guardrails for allowing us fail fast
     * // TODO: Find more
     */
    private void requireDifferentAcc(Long fromAccId, Long toAccId) {
        if (fromAccId == null || toAccId == null) {
            throw new IllegalArgumentException("Account IDs must not be null");
        }
        if (fromAccId.equals(toAccId)) {
            throw new IllegalArgumentException("Account IDs must be different");
        }
    }

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
