package my.sebastien.amurix.ledger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.sebastien.amurix.ledger.dto.TransactionResponse;
import my.sebastien.amurix.ledger.dto.TransferRequest;
import my.sebastien.amurix.ledger.entity.Transaction;
import my.sebastien.amurix.ledger.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transactions", description = "Create transfers and retrieve transaction history")
@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

  /*  @Operation(
            summary = "Create an idempotent transfer",
            description = "Debits the sender and credits the receiver and provide unique idempotency key",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created",
                            content = @Content(schema = @Schema(implementation = TransactionResponse.class))
                    )
            }
    )*/
    // TODO: Create and post method in our transaction service
   /* @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody TransferRequest transferRequest) {
        return toResponse()
    }
    */

    @Operation(summary = "Get a transaction by ID")
    @GetMapping("/{id}")
    public TransactionResponse get(@PathVariable Long id) {
        return toResponse(transactionService.get(id));
    }
    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .fromAccountId(transaction.getFromAccountId())
                .toAcccountId(transaction.getToAccountId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .idempotencyKey(transaction.getIdempKey())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

}