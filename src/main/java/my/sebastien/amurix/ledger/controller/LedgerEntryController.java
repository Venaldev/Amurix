package my.sebastien.amurix.ledger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import my.sebastien.amurix.ledger.dto.LedgerEntryResponse;
import my.sebastien.amurix.ledger.service.LedgerEntryService;
import my.sebastien.amurix.ledger.service.LedgerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Ledger Entries", description = "Read-only views of ledger lines, test")
@RestController
@RequestMapping("/ledger/entries")
@RequiredArgsConstructor
public class LedgerEntryController {

    private final LedgerEntryService entryService;
    private final LedgerService ledgerService; // Only for our manual post endpoint

    @Operation(
            summary = "List ledger entries for an account",
            description = "Returns a paged list of entries (DEBIT/CREDIT) affecting the given accountID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = LedgerEntryResponse.class)))
            }
    )
    @GetMapping("account/{accountId}")
    public Page<LedgerEntryResponse> listByAcc(
            @Parameter(description = "The account id to filter by") @PathVariable Long accountId,
            @Parameter(description = "Paging and sorting") Pageable pageable
            ) {
        return entryService.listByAccount(accountId, pageable);
    }

    @Operation(
            summary = "List ledger entries for a transaction",
            description = "To fetch both lines (DEBIT, CREDIT) for a specific txn",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = LedgerEntryResponse.class)))
            }
    )
    @GetMapping("/transaction/{transactionId}")
    public Page<LedgerEntryResponse> listByTransaction(@PathVariable Long transactionId, Pageable pageable) {
        return entryService.listByTransaction(transactionId, pageable);
    }
    @Operation(
            summary = "Get current balance for an account",
            description = "Computes balance  sum of (Credit - debit) from all ledger entries.",
            responses = { @ApiResponse(responseCode = "200", description = "OK") }
    )
    @GetMapping("/account/{accountId}/balance")
    public BigDecimal balanceOf(@PathVariable Long accountId) {
        return entryService.balanceOf(accountId);
    }
}
