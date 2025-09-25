package my.sebastien.amurix.ledger.repository;

import my.sebastien.amurix.ledger.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Look up transactions by our non-duped key
    Optional<Transaction> findByIdempKey(String idempKey);
    // Find a transaction from sender or receiver
    Page<Transaction> findByAnyId(Long fromAccountId, Long toAccountId, Pageable pageable);

    // Lookup between specific time blocks, good for reports
    Page<Transaction> findByTime(Instant fromIncluding, Instant toIncluding, Pageable pageable);
}
