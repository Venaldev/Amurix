package my.sebastien.amurix.ledger.dto;

import lombok.Builder;
import my.sebastien.amurix.ledger.entity.LedgerEntry;

import java.math.BigDecimal;
import java.time.Instant;
@Builder
public record LedgerEntryResponse(
        Long id,
        Long accountId,
        Long transactionId,
        LedgerEntry.Direction direction,
        BigDecimal amount,
        String currency,
        Instant createdAt
) {}
