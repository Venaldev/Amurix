package my.sebastien.amurix.ledger.dto;

import lombok.Builder;
import my.sebastien.amurix.ledger.entity.Account;
import my.sebastien.amurix.ledger.entity.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record TransactionResponse(
        Long id,
        Long fromAccountId,
        Long toAcccountId,
        BigDecimal amount,
        String currency,
        String idempotencyKey,
        Transaction.TransactStatus status,
        Instant createdAt,
        Instant updatedAt
) { }
