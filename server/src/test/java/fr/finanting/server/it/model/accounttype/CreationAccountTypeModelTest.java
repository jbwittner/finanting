package fr.finanting.server.it.model.accounttype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import fr.finanting.server.TestObjectFactory;
import fr.finanting.server.it.AbstractMotherTest;
import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositorie.AccountTypeRepository;

/**
 * Test class for creation of Account type
 */
public class CreationAccountTypeModelTest extends AbstractMotherTest {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {}

    /**
     * Test to check the good creation of account type
     */
    @Test
    public void createAccountTypeOk(){
        final AccountType accountType = new AccountType(this.factory.getRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT_TYPE));

        this.accountTypeRepository.saveAndFlush(accountType);

        final AccountType accountTypeSaved = this.accountTypeRepository.findByType(accountType.getType());

        Assertions.assertEquals(accountType, accountTypeSaved);
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type with a duplicate account type
     */
    @Test
    public void createDuplicateAccountTypeNOk(){
        final AccountType accountType = new AccountType(this.factory.getRandomAsciiString(TestObjectFactory.LENGTH_ACCOUNT_TYPE));

        this.accountTypeRepository.saveAndFlush(accountType);

        final AccountType duplicateAccountType = new AccountType(accountType.getType());

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            this.accountTypeRepository.saveAndFlush(duplicateAccountType);
        });
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type a null type
     */
    @Test
    public void createAccountTypeWithNullTypeNOk(){
        final AccountType accountType = new AccountType();
        
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            this.accountTypeRepository.saveAndFlush(accountType);
        });
        
    }

}
