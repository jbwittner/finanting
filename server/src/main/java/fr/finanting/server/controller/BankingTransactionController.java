package fr.finanting.server.controller;

import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.parameter.UpdateBankingTransactionParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.BankingTransactionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("bankingTransaction")
public class BankingTransactionController {

    private BankingTransactionService bankingTransactionService;

    @Autowired
    public BankingTransactionController(final BankingTransactionService bankingTransactionService){
        this.bankingTransactionService = bankingTransactionService;
    }

    @PostMapping("/createBankingTransaction")
    public BankingTransactionDTO createBankingTransaction(final Authentication authentication,
                                    @RequestBody final CreateBankingTransactionParameter createBankingTransactionParameter)
            throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingTransactionService.createBankingTransaction(createBankingTransactionParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/updateBankingTransaction")
    public void updateBankingTransaction(final Authentication authentication,
                                    @RequestBody final UpdateBankingTransactionParameter updateBankingTransactionParameter)
            throws BankingTransactionNotExistException, BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
            ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.bankingTransactionService.updateBankingTransaction(updateBankingTransactionParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getBankingTransaction/{id}")
    public BankingTransactionDTO getBankingTransaction(final Authentication authentication,
                                                        @PathVariable final Integer id)
            throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingTransactionService.getBankingTransaction(id, userDetailsImpl.getUsername());
    }

    @GetMapping("/getAccountBankingTransaction/{id}")
    public List<BankingTransactionDTO> getAccountBankingTransaction(final Authentication authentication,
                                                        @PathVariable final Integer id)
            throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException, BankingAccountNotExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.bankingTransactionService.getAccountBankingTransaction(id, userDetailsImpl.getUsername());
    }
    
}
