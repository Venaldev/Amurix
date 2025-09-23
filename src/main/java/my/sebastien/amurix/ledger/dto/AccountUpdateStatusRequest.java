package my.sebastien.amurix.ledger.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import my.sebastien.amurix.ledger.entity.Account;

@Builder
public record AccountUpdateStatusRequest(
        @NotNull(message = "status is required")
        Account.Status status
) {
}
