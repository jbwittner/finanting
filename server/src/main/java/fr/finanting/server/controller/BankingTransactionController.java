package fr.finanting.server.controller;

import fr.finanting.server.generated.api.BankingTransactionApi;
import fr.finanting.server.generated.model.BankingTransactionDTO;
import fr.finanting.server.generated.model.BankingTransactionParameter;
import fr.finanting.server.service.BankingTransactionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankingTransactionController extends MotherController implements BankingTransactionApi {

    private final BankingTransactionService bankingTransactionService;

    @Autowired
    public BankingTransactionController(final BankingTransactionService bankingTransactionService){
        super();
        this.bankingTransactionService = bankingTransactionService;
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> createBankingTransaction(final BankingTransactionParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.createBankingTransaction(body, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBankingTransaction(final Integer bankingTransactionId) {
        final String userName = this.getCurrentPrincipalName();
        this.bankingTransactionService.deleteAccountBankingTransaction(bankingTransactionId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankingTransactionDTO>> getBankingAccountTransaction(final Integer bankingAccountId) {
        final String userName = this.getCurrentPrincipalName();
        final List<BankingTransactionDTO> bankingTransactionDTOList = this.bankingTransactionService.getBankingAccountTransaction(bankingAccountId, userName);
        return new ResponseEntity<>(bankingTransactionDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> getBankingTransaction(final Integer bankingTransactionId) {
        final String userName = this.getCurrentPrincipalName();
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.getBankingTransaction(bankingTransactionId, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankingTransactionDTO> updateBankingTransaction(final Integer bankingTransactionId, final BankingTransactionParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionService.updateBankingTransaction(bankingTransactionId, body, userName);
        return new ResponseEntity<>(bankingTransactionDTO, HttpStatus.OK);
    }

}
