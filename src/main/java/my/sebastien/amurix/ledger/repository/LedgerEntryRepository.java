package my.sebastien.amurix.ledger.repository;

import my.sebastien.amurix.ledger.entity.LedgerEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    Page<LedgerEntry> findByAccountId(Long accountId, Pageable pageable);
    Page<LedgerEntry> findByTransactionId(Long transactionId, Pageable pageable);

    /**
     *
     * @param accountId
     * @return Top 50 account ids ordered by created at time
     */
    List<LedgerEntry> findTop50ByAccountId(Long accountId);

    @Query("""
      select coalesce(
        sum(case when e.direction='CREDIT' then e.amount else -e.amount end), 0)
      from LedgerEntry e
      where e.accountId = :accountId
    """)
    BigDecimal balanceOf(@Param("accountId") Long accountId);
}
