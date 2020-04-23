package fr.finanting.server.it.model.accounttype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.model.AccountType;
import fr.finanting.server.repositories.AccountTypeRepository;
import fr.finanting.server.it.model.ModelTest;

/**
 * Test class for creation of Account type
 */
public class CreationAccountTypeModelTest extends ModelTest {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    public static final String DUPLICATE_ACCOUNT_TYPE = "PAYPAL";

    /**
     * Test to check the good creation of account type
     */
    @Test
    public void createAccountTypeOk(){
        final AccountType accountType = factory.createRandomAccountType();

        accountTypeRepository.save(accountType);

        final AccountType deviceTypeSaved = accountTypeRepository.findByType(accountType.getType());

        Assertions.assertEquals(accountType, deviceTypeSaved);

    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type with a duplicate account type
     */
    @Test
    public void createDuplicatAccountTypeNOk(){
        final AccountType accountType = new AccountType(DUPLICATE_ACCOUNT_TYPE);
        final AccountType duplicateAccountType = new AccountType(DUPLICATE_ACCOUNT_TYPE);

        accountTypeRepository.save(accountType);

        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            accountTypeRepository.save(duplicateAccountType);
        });
    }

    /**
     * Test to check if the repository throw an exception when we try
     * to create a account type a null type
     */
    @Test
    public void createAccountTypeWithNullTypeNOk(){
        final AccountType deviceType = new AccountType(null);

        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            accountTypeRepository.save(deviceType);
        });
    }

}
