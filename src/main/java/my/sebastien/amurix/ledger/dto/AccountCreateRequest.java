package my.sebastien.amurix.ledger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AccountCreateRequest(
        @NotNull(message = "Owner id is required")
        Long ownerId,
        @NotNull(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be 3-letter code")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be uppercase 3-letter code")
        String currency
) {
}
