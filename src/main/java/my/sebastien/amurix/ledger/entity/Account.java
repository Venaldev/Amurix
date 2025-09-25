package my.sebastien.amurix.ledger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false, length = 3) // 'USD/GBP'
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Status status;

    @Column(nullable = false, updatable = false) // Never modify when an account was created
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public enum Status {
        ACTIVE,
        FROZEN,
        CLOSED
    }
    // Manage timestamps properly and handle defaults
    @PrePersist
    void onCreate() {
        final Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) this.status = Status.ACTIVE; // Set default status on create
    }
    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
