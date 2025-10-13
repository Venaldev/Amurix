package my.sebastien.amurix.ledger.dto;

import my.sebastien.amurix.ledger.entity.Account;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        Long id,
        Long fromAccountId,
        Long toAcccountId,
        BigDecimal amount,
        String currency,
        String idempotencyKey,
        Account.Status status,
        Instant createdAt,
        Instant updatedAt
) { }
