package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.AccountDTO;
import fr.finanting.server.dto.AccountsDTO;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateAccountParameter;
import fr.finanting.server.parameter.UpdateAccountParameter;
import fr.finanting.server.parameter.subpart.AddressParameter;
import fr.finanting.server.parameter.subpart.BankDetailsParameter;
import fr.finanting.server.repository.AccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.AccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class TestGetUserAccounts extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    private AccountServiceImpl accountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.accountServiceImpl = new AccountServiceImpl(accountRepository, groupRepository, userRepository);
    }

    @Test
    public void testGetUserAccountWithoutGroupAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final Account account1 = this.accountRepository.save(this.factory.getAccount(user));
        final Account account2 = this.accountRepository.save(this.factory.getAccount(user));
        final Account account3 = this.accountRepository.save(this.factory.getAccount(user));

        final AccountsDTO accountsDTO = this.accountServiceImpl.getUserAccounts(user.getUserName());

        Assertions.assertEquals(0, accountsDTO.getGroupAccountDTO().size());
        Assertions.assertEquals(3, accountsDTO.getUserAccountDTO().size());

        for(final AccountDTO accountDTO : accountsDTO.getUserAccountDTO()){
            boolean isPresent = true;
            if(accountDTO.getId().equals(account1.getId())){
                this.checkAccount(accountDTO, account1);
            } else if(accountDTO.getId().equals(account2.getId())){
                this.checkAccount(accountDTO, account2);
            } else if(accountDTO.getId().equals(account3.getId())){
                this.checkAccount(accountDTO, account3);
            } else {
                isPresent = false;
            }

            Assertions.assertTrue(isPresent);
        }

    }

    @Test
    public void testGetUserAccountWithoutUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final Account account1 = this.createGroupAccount(user);
        final Account account2 = this.createGroupAccount(user);
        final Account account3 = this.createGroupAccount(user);

        final AccountsDTO accountsDTO = this.accountServiceImpl.getUserAccounts(user.getUserName());

        Assertions.assertEquals(0, accountsDTO.getUserAccountDTO().size());
        Assertions.assertEquals(3, accountsDTO.getGroupAccountDTO().size());

        for(final AccountDTO accountDTO : accountsDTO.getGroupAccountDTO()){
            boolean isPresent = true;
            if(accountDTO.getId().equals(account1.getId())){
                this.checkAccount(accountDTO, account1);
            } else if(accountDTO.getId().equals(account2.getId())){
                this.checkAccount(accountDTO, account2);
            } else if(accountDTO.getId().equals(account3.getId())){
                this.checkAccount(accountDTO, account3);
            } else {
                isPresent = false;
            }

            Assertions.assertTrue(isPresent);
        }

    }

    private Account createGroupAccount(final User user){
        final Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());

        final Account account = this.accountRepository.save(this.factory.getAccount(group));
        final List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        group.setAccounts(accounts);

        this.groupRepository.save(group);

        final List<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);

        return account;
    }

    private void checkAccount(final AccountDTO accountDTO,
                              final Account account){

        Assertions.assertEquals(account.getAbbreviation(), accountDTO.getAbbreviation());
        Assertions.assertEquals(account.getInitialBalance(), accountDTO.getBalance());
        Assertions.assertEquals(account.getLabel(), accountDTO.getLabel());
        Assertions.assertEquals(account.getAddress().getCity(),
                accountDTO.getAddressDTO().getCity());
        Assertions.assertEquals(account.getAddress().getStreet(),
                accountDTO.getAddressDTO().getStreet());
        Assertions.assertEquals(account.getAddress().getAddress(),
                accountDTO.getAddressDTO().getAddress());
        Assertions.assertEquals(account.getAddress().getZipCode(),
                accountDTO.getAddressDTO().getZipCode());
        Assertions.assertEquals(account.getBankDetails().getAccountNumber(),
                accountDTO.getBankDetailsDTO().getAccountNumber());
        Assertions.assertEquals(account.getBankDetails().getIban(),
                accountDTO.getBankDetailsDTO().getIban());

    }
}