package my.sebastien.amurix.ledger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.sebastien.amurix.ledger.dto.AccountCreateRequest;
import my.sebastien.amurix.ledger.dto.AccountResponse;
import my.sebastien.amurix.ledger.dto.AccountUpdateStatusRequest;
import my.sebastien.amurix.ledger.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Posts a new and unique account
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create (@Valid @RequestBody AccountCreateRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAcc(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<AccountResponse> listByOwner(@PathVariable Long ownerId) {
        return accountService.listByOwner(ownerId);
    }

    @PatchMapping("/{id}/status")
    public AccountResponse updateStatus(@PathVariable Long id, @Valid @RequestBody AccountUpdateStatusRequest request) {
        return accountService.updateStatus(id, request);
    }

}
