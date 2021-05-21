package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.BankingAccountApi;
import fr.finanting.server.codegen.model.BankingAccountDTO;
import fr.finanting.server.codegen.model.BankingAccountParameter;
import fr.finanting.server.codegen.model.UpdateBankingAccountParameter;
import fr.finanting.server.service.BankingAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankingAccountController extends MotherController implements BankingAccountApi {

    private final BankingAccountService bankingAccountService;

    @Autowired
    public BankingAccountController(final BankingAccountService bankingAccountService){
        super();
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public ResponseEntity<BankingAccountDTO> createBankingAccount(final BankingAccountParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final BankingAccountDTO bankingAccountDTO = this.bankingAccountService.createAccount(body, userName);
        return new ResponseEntity<>(bankingAccountDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBankingAccount(final Integer bankingAccountId) {
        final String userName = this.getCurrentPrincipalName();
        this.bankingAccountService.deleteAccount(bankingAccountId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankingAccountDTO> getBankingAccount(final Integer bankingAccountId) {
        final String userName = this.getCurrentPrincipalName();
        final BankingAccountDTO bankingAccountDTO = this.bankingAccountService.getBankingAccount(bankingAccountId, userName);
        return new ResponseEntity<>(bankingAccountDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankingAccountDTO>> getGroupBankingAccounts(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        final List<BankingAccountDTO> bankingAccountDTOList = this.bankingAccountService.getGroupBankingAccounts(groupId, userName);
        return new ResponseEntity<>(bankingAccountDTOList, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<BankingAccountDTO>> getUserBankingAccounts() {
        final String userName = this.getCurrentPrincipalName();
        final List<BankingAccountDTO> bankingAccountDTOList = this.bankingAccountService.getUserBankingAccounts(userName);
        return new ResponseEntity<>(bankingAccountDTOList, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BankingAccountDTO> updateBankingAccount(final Integer bankingAccountId, final UpdateBankingAccountParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final BankingAccountDTO bankingAccountDTO = this.bankingAccountService.updateAccount(bankingAccountId, body, userName);
        return new ResponseEntity<>(bankingAccountDTO, HttpStatus.CREATED);
    }


}
