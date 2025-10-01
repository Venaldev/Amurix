package my.sebastien.amurix.ledger.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sebastien.amurix.ledger.dto.LedgerEntryResponse;
import my.sebastien.amurix.ledger.entity.LedgerEntry;
import my.sebastien.amurix.ledger.repository.LedgerEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class LedgerEntryService {

    private final LedgerEntryRepository repository;

    @Transactional(readOnly = true)
    public Page<LedgerEntryResponse> listByAccount(Long accountId, Pageable pageable) {
        return repository.findByAccountId(accountId, pageable).map(this::toResponse);
    }

    @Transactional (readOnly = true)
    public Page<LedgerEntryResponse> listByTransaction(Long transactionId, Pageable pageable) {
        return repository.findByTransactionId(transactionId, pageable).map(this::toResponse);
    }

    @Transactional (readOnly = true)
    public BigDecimal balanceOf(Long acocuntId) {
        return repository.balanceOf(acocuntId);
    }

    @Transactional
    public LedgerEntry save(LedgerEntry entry) {;
        // TODO: Log saved entry
        return repository.save(entry);
    }
    private LedgerEntryResponse toResponse(LedgerEntry entry) {
        return LedgerEntryResponse.builder()
                .id(entry.getId())
                .accountId(entry.getAccountId())
                .transactionId(entry.getTransactionId())
                .direction(entry.getDirection())
                .amount(entry.getAmount())
                .currency(entry.getCurrency())
                .createdAt(entry.getCreatedAt())
                .build();
    }
}
