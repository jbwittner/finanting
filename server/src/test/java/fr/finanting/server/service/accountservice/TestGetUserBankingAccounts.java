package fr.finanting.server.service.accountservice;

import fr.finanting.server.dto.BankingAccountDTO;
import fr.finanting.server.dto.BankingAccountsDTO;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingAccountServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestGetUserBankingAccounts extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    private BankingAccountServiceImpl bankingAccountServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingAccountServiceImpl = new BankingAccountServiceImpl(bankingAccountRepository, groupRepository, userRepository);
    }

    @Test
    public void testGetUserAccountWithoutGroupAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount1 = this.bankingAccountRepository.save(this.factory.getBankingAccount(user));
        final BankingAccount bankingAccount2 = this.bankingAccountRepository.save(this.factory.getBankingAccount(user));
        final BankingAccount bankingAccount3 = this.bankingAccountRepository.save(this.factory.getBankingAccount(user));

        final BankingAccountsDTO bankingAccountsDTO = this.bankingAccountServiceImpl.getUserBankingAccounts(user.getUserName());

        Assertions.assertEquals(0, bankingAccountsDTO.getGroupAccountDTO().size());
        Assertions.assertEquals(3, bankingAccountsDTO.getUserAccountDTO().size());

        for(final BankingAccountDTO bankingAccountDTO : bankingAccountsDTO.getUserAccountDTO()){
            boolean isPresent = true;
            if(bankingAccountDTO.getId().equals(bankingAccount1.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount1);
            } else if(bankingAccountDTO.getId().equals(bankingAccount2.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount2);
            } else if(bankingAccountDTO.getId().equals(bankingAccount3.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount3);
            } else {
                isPresent = false;
            }

            Assertions.assertTrue(isPresent);
        }

    }

    @Test
    public void testGetUserAccountWithoutUserAccount() {
        final User user = this.userRepository.save(this.factory.getUser());
        final BankingAccount bankingAccount1 = this.createGroupAccount(user);
        final BankingAccount bankingAccount2 = this.createGroupAccount(user);
        final BankingAccount bankingAccount3 = this.createGroupAccount(user);

        final BankingAccountsDTO bankingAccountsDTO = this.bankingAccountServiceImpl.getUserBankingAccounts(user.getUserName());

        Assertions.assertEquals(0, bankingAccountsDTO.getUserAccountDTO().size());
        Assertions.assertEquals(3, bankingAccountsDTO.getGroupAccountDTO().size());

        for(final BankingAccountDTO bankingAccountDTO : bankingAccountsDTO.getGroupAccountDTO()){
            boolean isPresent = true;
            if(bankingAccountDTO.getId().equals(bankingAccount1.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount1);
            } else if(bankingAccountDTO.getId().equals(bankingAccount2.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount2);
            } else if(bankingAccountDTO.getId().equals(bankingAccount3.getId())){
                this.checkAccount(bankingAccountDTO, bankingAccount3);
            } else {
                isPresent = false;
            }

            Assertions.assertTrue(isPresent);
        }

    }

    private BankingAccount createGroupAccount(final User user){
        final Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());

        final BankingAccount bankingAccount = this.bankingAccountRepository.save(this.factory.getBankingAccount(group));
        final List<BankingAccount> bankingAccounts = new ArrayList<>();
        bankingAccounts.add(bankingAccount);
        group.setAccounts(bankingAccounts);

        this.groupRepository.save(group);

        final List<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);

        return bankingAccount;
    }

    private void checkAccount(final BankingAccountDTO bankingAccountDTO,
                              final BankingAccount bankingAccount){

        Assertions.assertEquals(bankingAccount.getAbbreviation(), bankingAccountDTO.getAbbreviation());
        Assertions.assertEquals(bankingAccount.getInitialBalance(), bankingAccountDTO.getBalance());
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