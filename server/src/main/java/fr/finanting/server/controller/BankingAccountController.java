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
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public ResponseEntity<BankingAccountDTO> createBankingAccount(BankingAccountParameter body) {
        String userName = this.getCurrentPrincipalName();
        BankingAccountDTO bankingAccountDTO = this.bankingAccountService.createAccount(body, userName);
        return new ResponseEntity<>(bankingAccountDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBankingAccount(Integer bankingAccountId) {
        String userName = this.getCurrentPrincipalName();
        this.bankingAccountService.deleteAccount(bankingAccountId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankingAccountDTO>> getGroupBankingAccounts(Integer groupId) {
        String userName = this.getCurrentPrincipalName();
        List<BankingAccountDTO> bankingAccountDTOList = this.bankingAccountService.getGroupBankingAccounts(groupId, userName);
        return new ResponseEntity<>(bankingAccountDTOList, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<BankingAccountDTO>> getUserBankingAccounts() {
        String userName = this.getCurrentPrincipalName();
        List<BankingAccountDTO> bankingAccountDTOList = this.bankingAccountService.getUserBankingAccounts(userName);
        return new ResponseEntity<>(bankingAccountDTOList, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BankingAccountDTO> updateBankingAccount(Integer bankingAccountId, UpdateBankingAccountParameter body) {
        String userName = this.getCurrentPrincipalName();
        BankingAccountDTO bankingAccountDTO = this.bankingAccountService.updateAccount(bankingAccountId, body, userName);
        return new ResponseEntity<>(bankingAccountDTO, HttpStatus.CREATED);
    }


}
