package fr.finanting.server.controller;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.dto.BankingAccountsDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.BankingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bankingAccount")
public class BankingAccountController {

    private final BankingAccountService bankingAccountService;

    @Autowired
    public BankingAccountController(final BankingAccountService bankingAccountService){
        this.bankingAccountService = bankingAccountService;
    }

    @PostMapping("/updateAccount")
    public BankingAccountDTO updateAccount(final Authentication authentication,
                                    @RequestBody final UpdateAccountParameter updateAccountParameter)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.updateAccount(updateAccountParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(final Authentication authentication,
                                    @RequestBody final DeleteAccountParameter deleteAccountParameter)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.bankingAccountService.deleteAccount(deleteAccountParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/createAccount")
    public BankingAccountDTO createAccount(final Authentication authentication,
                                    @RequestBody final CreateBankingAccountParameter createAccountParameter)
            throws UserNotExistException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.createAccount(createAccountParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getUserAccounts")
    public BankingAccountsDTO getUserAccounts(final Authentication authentication)
            throws UserNotExistException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.getUserAccounts(userDetailsImpl.getUsername());
    }

    @GetMapping("/getAccount/{id}")
    public BankingAccountDTO getAccount(final Authentication authentication,
                                 @PathVariable final Integer id)
            throws BankingAccountNotExistException, UserNotInGroupException, NotUserAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.getAccount(id, userDetailsImpl.getUsername());
    }


}
