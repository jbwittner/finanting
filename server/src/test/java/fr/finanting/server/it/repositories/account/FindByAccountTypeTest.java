package fr.finanting.server.it.repositories.account;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.model.Account;
import fr.finanting.server.model.AccountType;

/**
 * Class to check the findByType method of AccountTypeRepository
 */
public class FindByAccountTypeTest extends MotherAccountRepositoryTest {

    /**
     * Test to check the findBy method when the accountType are in the database
     */
    @Test
    public void findByAccountTypeTestOk(){
        List<Account> accountListSaved;
        List<Account> accountListRef;

        for(final AccountType accountType: this.accountTypeList){
            accountListSaved = this.accountRepository.findByAccountType(accountType);
            accountListRef = this.accountMapByType.get(accountType);
            Assertions.assertEquals(accountListRef, accountListSaved);
        }
    }

    @Test
    public void findByAccountTypeNotUsedTestOk(){
        final AccountType accountType = this.factory.createAccountType();
        this.accountTypeRepository.saveAndFlush(accountType);

        final List<Account> account = this.accountRepository.findByAccountType(accountType);

        Assertions.assertEquals(0,account.size());
    }

}