package fr.finanting.server.it.model.accounttype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.it.AbstractMotherTest;
import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositorie.AccountTypeRepository;

/**
 * Test class for creation of Account type
 */
public class CreationAccountTypeModelTest extends AbstractMotherTest {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    AccountType accountTypeTest;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDataBeforeEach() {
        this.accountTypeTest = this.factory.createRandomAccountType();
    }

    /**
     * Test to check the good creation of account type
     */
    @Test
    public void createAccountTypeOk(){
        accountTypeRepository.saveAndFlush(this.accountTypeTest);

        final AccountType deviceTypeSaved = accountTypeRepository.findByType(this.accountTypeTest.getType());

        Assertions.assertEquals(this.accountTypeTest, deviceTypeSaved);
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type with a duplicate account type
     */
    @Test
    public void createDuplicateAccountTypeNOk(){
        final AccountType duplicateAccountType = this.factory.createAccountType(this.accountTypeTest.getType());

        accountTypeRepository.saveAndFlush(this.accountTypeTest);

        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            accountTypeRepository.saveAndFlush(duplicateAccountType);
        });
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type a null type
     */
    @Test
    public void createAccountTypeWithNullTypeNOk(){
        final AccountType deviceType = this.factory.createAccountType(null);
        
        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            accountTypeRepository.saveAndFlush(deviceType);
        });
        
    }

}
