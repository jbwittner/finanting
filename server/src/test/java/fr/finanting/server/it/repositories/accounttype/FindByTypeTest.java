package fr.finanting.server.it.repositories.accounttype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.model.AccountType;

/**
 * Class to check the findByType method of AccountTypeRepository
 */
public class FindByTypeTest extends MotherAccountTypeRepositoryTest {

    /**
     * Test to check the findBy method when the accountType are in the database
     */
    @Test
    public void findByTypeOk(){
        AccountType accountTypeSaved;

        for(final AccountType accountType: this.accountTypeList){
            accountTypeSaved = this.accountTypeRepository.findByType(accountType.getType());
            Assertions.assertEquals(accountType, accountTypeSaved);
        }
    }

    /**
     * Test to check the findBy method when the accountType the input are null
     */
    @Test
    public void findByTypeNullNok(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.accountTypeRepository.findByType(null);
        });
    }

    /**
     * Test to check the findBy method when the accountType are not in the database
     */
    @Test
    public void findByTypeNotExistOk(){
        final String randomString = this.factory.getUniqueRandomAsciiString(10);

        final AccountType accountType = this.accountTypeRepository.findByType(randomString);

        Assertions.assertNull(accountType);
    }

}