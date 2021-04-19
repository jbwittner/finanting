package fr.finanting.server.controller;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingAccountParameter;
import fr.finanting.server.parameter.DeleteBankingAccountParameter;
import fr.finanting.server.parameter.UpdateBankingAccountParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.BankingAccountService;

import java.util.List;

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
                                    @RequestBody final UpdateBankingAccountParameter updateBankingAccountParameter)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.updateAccount(updateBankingAccountParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(final Authentication authentication,
                                    @RequestBody final DeleteBankingAccountParameter deleteBankingAccountParameter)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.bankingAccountService.deleteAccount(deleteBankingAccountParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/createAccount")
    public BankingAccountDTO createAccount(final Authentication authentication,
                                    @RequestBody final CreateBankingAccountParameter createBankingAccountParameter)
            throws UserNotExistException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.createAccount(createBankingAccountParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getUserBankingAccounts")
    public List<BankingAccountDTO> getUserBankingAccounts(final Authentication authentication)
            throws UserNotExistException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.getUserBankingAccounts(userDetailsImpl.getUsername());
    }

    @GetMapping("/getBankingAccount/{id}")
    public BankingAccountDTO getBankingAccount(final Authentication authentication,
                                 @PathVariable final Integer id)
            throws BankingAccountNotExistException, UserNotInGroupException, NotUserBankingAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingAccountService.getBankingAccount(id, userDetailsImpl.getUsername());
    }


}
