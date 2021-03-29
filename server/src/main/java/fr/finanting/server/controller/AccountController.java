package fr.finanting.server.controller;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.DeleteAccountParameter;
import fr.finanting.server.parameter.GroupCreationParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Account controller
 */
@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/updateAccount")
    public AccountDTO updateAccount(final Authentication authentication,
                                    @RequestBody final UpdateAccountParameter updateAccountParameter)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.accountService.updateAccount(updateAccountParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteAccount")
    public void deleteAccount(final Authentication authentication,
                                    @RequestBody final DeleteAccountParameter deleteAccountParameter)
            throws AccountNotExistException, NotAdminGroupException, NotUserAccountException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.accountService.deleteAccount(deleteAccountParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/createAccount")
    public AccountDTO createAccount(final Authentication authentication,
                                    @RequestBody final CreateAccountParameter createAccountParameter)
            throws UserNotExistException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.accountService.createAccount(createAccountParameter, userDetailsImpl.getUsername());
    }


}
