package my.sebastien.amurix.ledger.dto;

import my.sebastien.amurix.ledger.entity.Account;

import java.time.Instant;

public record AccountResponse(
        Long id,
        Long ownerId,
        String currency,
        Account.Status status,
        Instant createdAt,
        Instant updatedAt
) {
}
