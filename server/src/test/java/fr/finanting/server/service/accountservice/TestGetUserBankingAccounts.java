package fr.finanting.server.service.accountservice;

import fr.finanting.server.codegen.model.BankingAccountDTO;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.*;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestGetUserBankingAccounts extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository, bankingTransactionRepository);
    }

    @Test
    public void testGetUserAccountWithoutGroupAccount() {
        final User user = this.testFactory.getUser();

        final BankingAccount bankingAccount1 = this.testFactory.getBankingAccount(user);
        this.testFactory.getBankingTransaction(user, bankingAccount1, false);
        final BankingAccount bankingAccount2 = this.testFactory.getBankingAccount(user);
        this.testFactory.getBankingTransaction(user, bankingAccount2, false);
        this.testFactory.getBankingTransaction(user, bankingAccount2, false);
        final BankingAccount bankingAccount3 = this.testFactory.getBankingAccount(user);
        this.testFactory.getBankingTransaction(user, bankingAccount3, false);
        this.testFactory.getBankingTransaction(user, bankingAccount3, false);
        this.testFactory.getBankingTransaction(user, bankingAccount3, false);
        final BankingAccount bankingAccount4 = this.testFactory.getBankingAccount(user);

        final List<BankingAccountDTO> bankingAccountsDTO = this.bankingAccountServiceImpl.getUserBankingAccounts(user.getUserName());

        Assertions.assertEquals(4, bankingAccountsDTO.size());

        for(final BankingAccountDTO bankingAccountDTO : bankingAccountsDTO){
            boolean isPresent = true;
            if(bankingAccountDTO.getId().equals(bankingAccount1.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount1);
            } else if(bankingAccountDTO.getId().equals(bankingAccount2.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount2);
            } else if(bankingAccountDTO.getId().equals(bankingAccount3.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount3);
            } else if(bankingAccountDTO.getId().equals(bankingAccount4.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount4);
            } else {
                isPresent = false;
            }

            Assertions.assertTrue(isPresent);
        }

    }

    @Test
    public void testGetUserAccountWithoutUserAccount() {
        final User user = this.testFactory.getUser();

        final List<BankingAccountDTO> bankingAccountsDTO = this.bankingAccountServiceImpl.getUserBankingAccounts(user.getUserName());

        Assertions.assertEquals(0, bankingAccountsDTO.size());

    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount){

        final List<BankingTransaction> bankingTransactionList = this.bankingTransactionRepository.findByAccount(bankingAccount);

        final Double balance = bankingAccount.getInitialBalance() + bankingTransactionList.stream().mapToDouble(BankingTransaction::getAmount).sum();

        Assertions.assertEquals(bankingAccount.getAbbreviation(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(balance, bankingAccountDTO.getBalance());
        Assertions.assertEquals(bankingAccount.getLabel(), bankingAccountDTO.getLabel());
        Assertions.assertEquals(bankingAccount.getAddress().getCity(),
                bankingAccountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(bankingAccount.getAddress().getStreet(),
                bankingAccountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(bankingAccount.getAddress().getAddress(),
                bankingAccountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(bankingAccount.getAddress().getZipCode(),
                bankingAccountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(bankingAccount.getBankDetails().getAccountNumber(),
                bankingAccountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(bankingAccount.getBankDetails().getIban(),
                bankingAccountDTO.getBankDetailsDTO().getIban());
        Assertions.assertEquals(bankingAccount.getDefaultCurrency().getIsoCode(),
                bankingAccountDTO.getCurrencyDTO().getIsoCode());

    }
}