package fr.finanting.server.service.accountservice;

import fr.finanting.server.codegen.model.BankingAccountDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.*;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestGetGroupBankingAccounts extends AbstractMotherIntegrationTest {

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

    private User user;
    private Group group;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository, currencyRepository, bankingTransactionRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
    }

    @Test
    public void testGetGroupAccount() throws UserNotInGroupException, GroupNotExistException {
        
        final BankingAccount bankingAccount1 = this.testFactory.getBankingAccount(this.group);
        this.testFactory.getBankingTransaction(this.group, bankingAccount1, false);
        final BankingAccount bankingAccount2 = this.testFactory.getBankingAccount(this.group);
        this.testFactory.getBankingTransaction(this.group, bankingAccount2, false);
        this.testFactory.getBankingTransaction(this.group, bankingAccount2, false);
        final BankingAccount bankingAccount3 = this.testFactory.getBankingAccount(this.group);
        this.testFactory.getBankingTransaction(this.group, bankingAccount3, false);
        this.testFactory.getBankingTransaction(this.group, bankingAccount3, false);
        this.testFactory.getBankingTransaction(this.group, bankingAccount3, false);
        final BankingAccount bankingAccount4 = this.testFactory.getBankingAccount(this.group);

        final List<BankingAccountDTO> bankingAccountsDTO =
                this.bankingAccountServiceImpl.getGroupBankingAccounts(this.group.getId(), this.user.getUserName());

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
    public void testGetGroupAccountUserNotInGroup() {
        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingAccountServiceImpl.getGroupBankingAccounts(this.group.getId(), otherUser.getUserName()));

    }

    @Test
    public void testGetGroupAccountNotExisted() {
        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.bankingAccountServiceImpl.getGroupBankingAccounts(this.testFactory.getRandomInteger(), this.user.getUserName()));
    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount){

        List<BankingTransaction> bankingTransactionList = this.bankingTransactionRepository.findByAccount(bankingAccount);

        Double balance = bankingAccount.getInitialBalance() + bankingTransactionList.stream().mapToDouble(BankingTransaction::getAmount).sum();

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

    }
}