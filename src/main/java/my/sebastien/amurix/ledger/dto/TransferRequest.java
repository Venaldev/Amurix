package my.sebastien.amurix.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull Long fromAccountId,
        @NotNull Long toAccountId,
        @NotNull @Positive BigDecimal amount,
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be uppercase 3-letter code (USD)")
        String currency,
        @NotBlank
        String idempotencyKey
        ) {
}
