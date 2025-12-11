package my.sebastien.amurix.ledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

//TODO: Add detailed NotNull validation messages
@Entity(name = "LedgerEntry") // JPQL does not want to recognize this in our repo
@Table(
        name = "ledger_entries",
        indexes = {
                @Index(name = "idx_ledger_account", columnList = "account_id"),
                @Index(name = "idx_ledger_transaction", columnList = "transaction_id"),
                @Index(name = "idx_ledger_created_at", columnList = "created_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ledger_account_transaction_direction", columnNames = {"account_id", "transaction_id", "direction"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LedgerEntry {
    // Will be our primary key for ledgers
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Direction direction;

    @NotNull
    @Positive(message = "Amount must be positive")
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code")
    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
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
