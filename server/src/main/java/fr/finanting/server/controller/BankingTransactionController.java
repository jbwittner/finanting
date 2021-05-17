package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.BankingTransactionApi;
import fr.finanting.server.codegen.model.BankingTransactionDTO;
import fr.finanting.server.codegen.model.BankingTransactionParameter;
import fr.finanting.server.service.BankingTransactionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankingTransactionController extends MotherController implements BankingTransactionApi {

    private BankingTransactionService bankingTransactionService;

    @Autowired
    public BankingTransactionController(final BankingTransactionService bankingTransactionService){
        this.bankingTransactionService = bankingTransactionService;
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> createBankingTransaction(BankingTransactionParameter body) {
        String userName = this.getCurrentPrincipalName();
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.createBankingTransaction(body, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBankingTransaction(Integer bankingTransactionId) {
        String userName = this.getCurrentPrincipalName();
        this.bankingTransactionService.deleteAccountBankingTransaction(bankingTransactionId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankingTransactionDTO>> getBankingAccountTransaction(Integer bankingAccountId) {
        String userName = this.getCurrentPrincipalName();
        List<BankingTransactionDTO> bankingTransactionDTOList = this.bankingTransactionService.getBankingAccountTransaction(bankingAccountId, userName);
        return new ResponseEntity<>(bankingTransactionDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> getBankingTransaction(Integer bankingTransactionId) {
        String userName = this.getCurrentPrincipalName();
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.getBankingTransaction(bankingTransactionId, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> updateBankingTransaction(Integer bankingTransactionId, BankingTransactionParameter body) {
        String userName = this.getCurrentPrincipalName();
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.updateBankingTransaction(bankingTransactionId, body, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.OK);
    }

    /*
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

    @DeleteMapping("/deleteAccountBankingTransaction/{id}")
    public void deleteAccountBankingTransaction(final Authentication authentication,
                                                        @PathVariable final Integer id)
            throws BankingTransactionNotExistException, NotUserElementException, UserNotInGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.bankingTransactionService.deleteAccountBankingTransaction(id, userDetailsImpl.getUsername());
    }

     */
    
}
