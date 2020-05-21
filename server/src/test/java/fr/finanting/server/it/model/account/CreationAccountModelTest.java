package fr.finanting.server.it.model.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import fr.finanting.server.TestObjectFactory;
import fr.finanting.server.it.AbstractMotherTest;
import fr.finanting.server.model.Account;
import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositorie.AccountRepository;
import fr.finanting.server.repositorie.AccountTypeRepository;

/**
 * Test class for creation of Account type
 */
public class CreationAccountModelTest extends AbstractMotherTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    AccountType accountTypeOne;
    AccountType accountTypeTwo;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {
        this.accountTypeOne = this.factory.createAccountType();
        this.accountTypeTwo = this.factory.createAccountType();
        this.accountTypeRepository.save(accountTypeOne);
        this.accountTypeRepository.save(accountTypeTwo);
        this.accountTypeRepository.flush();
    }

    /**
     * Test to check the good creation of account type
     */
    @Test
    public void createOk(){
        final Account account = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                            this.accountTypeOne);

        this.accountRepository.saveAndFlush(account);

        final Account accountSaved = accountRepository.findByName(account.getName());

        Assertions.assertEquals(account, accountSaved);
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type with a duplicate account type
     */
    @Test
    public void createDuplicateNOk(){
        final Account account = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                            this.accountTypeOne);

        this.accountRepository.saveAndFlush(account);

        final Account duplicateAccount = new Account(account.getName(), account.getAccountType());

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(duplicateAccount);
        });
    }

    @Test
    public void createDuplicateAccountNameNOk(){
        final Account account = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                            this.accountTypeOne);

        this.accountRepository.saveAndFlush(account);

        final Account duplicateAccount = new Account(account.getName(), this.accountTypeTwo);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(duplicateAccount);
        });
    }

    @Test
    public void createDuplicateAccountTypeOk(){
        final Account account = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                            this.accountTypeOne);

        this.accountRepository.saveAndFlush(account);

        final Account duplicateAccount = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                                    this.accountTypeTwo);

        accountRepository.saveAndFlush(duplicateAccount);

        final Account accountSaved = accountRepository.findByName(account.getName());
        final Account duplicateAccountSaved = accountRepository.findByName(duplicateAccount.getName());

        Assertions.assertEquals(account, accountSaved);
        Assertions.assertEquals(duplicateAccount, duplicateAccountSaved);
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type a null type
     */
    @Test
    public void createWithNullNameNOk(){
        final Account account = new Account(null, this.accountTypeOne);
        
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(account);
        });
        
    }

    @Test
    public void createWithNullTypeNOk(){
        final Account account = new Account(this.factory.getUniqueRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT),
                                            null);
        
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(account);
        });
        
    }

    @Test
    public void createWithAllNullNOk(){
        final Account account = new Account();
        
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(account);
        });
        
    }

}
