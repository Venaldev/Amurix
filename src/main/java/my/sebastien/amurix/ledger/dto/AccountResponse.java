package my.sebastien.amurix.ledger.dto;

import lombok.Builder;
import my.sebastien.amurix.ledger.entity.Account;

import java.time.Instant;

@Builder
public record AccountResponse(
        Long id,
        Long ownerId,
        String currency,
        Account.Status status,
        Instant createdAt,
        Instant updatedAt
) {
}
