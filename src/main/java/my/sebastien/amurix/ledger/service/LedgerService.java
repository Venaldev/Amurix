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

    }
}
