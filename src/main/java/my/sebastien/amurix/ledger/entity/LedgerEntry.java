package my.sebastien.amurix.ledger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

// TODO: Indexes for searching the table
@Table(name = "ledger_entries")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private Long transactionId;

    private Direction direction;

    private BigDecimal amount;

    private String currency;

    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public enum Direction {
        CREDIT,
        DEBIT
    }

}
