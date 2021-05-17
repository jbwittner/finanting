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
@RequestMapping("bankingAccount")
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

    /*
    @PostMapping("/updateAccount")
    public BankingAccountDTO updateAccount(final Authentication authentication,
                                    @RequestBody final UpdateBankingAccountParameter updateBankingAccountParameter)
            throws BankingAccountNotExistException, NotAdminGroupException, NotUserBankingAccountException, CurrencyNotExistException {
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
            throws UserNotExistException, GroupNotExistException, CurrencyNotExistException {
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

     */

}
