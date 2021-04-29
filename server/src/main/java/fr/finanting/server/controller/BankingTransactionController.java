package fr.finanting.server.controller;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.BankingTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("bankingTransaction")
public class BankingTransactionController {

    private BankingTransactionService bankingTransactionService;

    @Autowired
    public BankingTransactionController(BankingTransactionService bankingTransactionService){
        this.bankingTransactionService = bankingTransactionService;
    }

    @PostMapping("/createBankingTransaction")
    public BankingTransactionDTO createBankingTransaction(final Authentication authentication,
                                    @RequestBody final CreateBankingTransactionParameter createBankingTransactionParameter)
            throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingTransactionService.createBankingTransaction(createBankingTransactionParameter, userDetailsImpl.getUsername());
    }
    
}
