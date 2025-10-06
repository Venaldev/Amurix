package my.sebastien.amurix.ledger.service;

import lombok.RequiredArgsConstructor;
import my.sebastien.amurix.ledger.entity.LedgerEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Transaction service for posting balanced pair of entries
 */
@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerEntryService entryService;

    @Transactional
    public void postTransfer(Long fromAccId, Long toAccId, BigDecimal amount, String currency, Long transcId) {
        // Debit from the sender
        LedgerEntry debit = LedgerEntry.builder()
                .accountId(fromAccId)
                .transactionId(transcId)
                .direction(LedgerEntry.Direction.DEBIT)
                .currency(currency)
                .amount(amount)
                .build();
        entryService.save(debit);

        // Credit the receiver
        LedgerEntry credit = LedgerEntry.builder()
                .accountId(toAccId)
                .transactionId(transcId)
                .direction(LedgerEntry.Direction.CREDIT)
                .amount(amount)
                .currency(currency)
                .build();
        entryService.save(credit);
    }
}
