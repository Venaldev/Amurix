package my.sebastien.amurix.ledger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created indexes to decrease loading times during scale
 * Make sure transactions are always unique!
 */
@Entity
@Table(name = "transactions",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_transactions_idemp_key", columnNames = "idemp_key")
        },
        indexes = {
            @Index(name ="idx_tx_from_account", columnList = "from_account_id"),
            @Index(name = "idx_tx_to_account", columnList = "to_account_id"),
            @Index(name = "idx_tx_created_at", columnList = "created_at")
        }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_account_id", nullable = false)
    private Long fromAccountId;

    @NotNull
    @Column(name = "to_account_id", nullable = false)
    private Long toAccountId;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be uppercase 3-letter code!")
    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TransactStatus status;

    @NotBlank
    @Column(name = "idemp_key", nullable = false, unique = true, length = 128)
    private String idempKey;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public enum TransactStatus {
        PENDING,
        POSTED,
        REJECTED
    }
    // Manage timestamps properly
    @PrePersist
    void onCreate() {
        final Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = TransactStatus.PENDING; // New request must go through a pending phase
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }



}
