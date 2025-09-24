package my.sebastien.amurix.ledger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sebastien.amurix.ledger.dto.AccountCreateRequest;
import my.sebastien.amurix.ledger.dto.AccountResponse;
import my.sebastien.amurix.ledger.dto.AccountUpdateStatusRequest;
import my.sebastien.amurix.ledger.entity.Account;
import my.sebastien.amurix.ledger.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    /** Create a new account with our default ACTIVE status */
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {
        Account account = Account.builder()
                .ownerId(request.ownerId())
                .currency(request.currency())
                .build();

        Account savedAcc = accountRepository.save(account);
        log.info("Created account id={} ownerId={} currency={}", savedAcc.getId(), savedAcc.getOwnerId(), savedAcc.getCurrency());
        return toResponse(savedAcc);
    }
    /** Fetch single account or throw if not found */
    @Transactional(readOnly = true)
    public AccountResponse getAcc(Long id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account now found " + id));
        return toResponse(acc);
    }
    /** List accounts by owner */
    @Transactional(readOnly = true)
    public List<AccountResponse> listByOwner(Long ownerId) {
        return accountRepository.findOwnerById(ownerId).stream()
                .map(this::toResponse)
                .toList();
    }

    /** Update status of an account, ACTIVE OR FROZEN */
    @Transactional
    public AccountResponse updateStatus(Long id, AccountUpdateStatusRequest request) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found " + id));

        Account.Status newStatus = request.status();
        /** Need to implement system for re-allowing closed accounts */
        if (acc.getStatus() == Account.Status.CLOSED && newStatus != Account.Status.CLOSED) {
            throw new IllegalStateException("Cannot reopen a CLOSED account!");
        }

        acc.setStatus(newStatus);
        Account newSaved = accountRepository.save(acc);
        log.info("Updated account id={} status={}", newSaved.getId(), newSaved.getStatus());
        return toResponse(newSaved);

    }
    /** For mapping */
    private AccountResponse toResponse(Account acc) {
        return AccountResponse.builder()
                .id(acc.getId())
                .ownerId(acc.getOwnerId())
                .currency(acc.getCurrency())
                .status(acc.getStatus())
                .createdAt(acc.getCreatedAt())
                .updatedAt(acc.getUpdatedAt())
                .build();
    }
}
