package fr.finanting.server.it.repositories.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.model.Account;

/**
 * Class to check the findByType method of AccountTypeRepository
 */
public class FindByNameTest extends MotherAccountRepositoryTest {

    /**
     * Test to check the findBy method when the accountType are in the database
     */
    @Test
    public void findByNameOk(){
        Account accountSaved;

        for(final Account account: this.accountList){
            accountSaved = this.accountRepository.findByName(account.getName());
            Assertions.assertEquals(account, accountSaved);
        }
    }

    /**
     * Test to check the findBy method when the accountType the input are null
     */
    @Test
    public void findByNameNullNok(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.accountRepository.findByName(null);
        });
    }

    /**
     * Test to check the findBy method when the accountType are not in the database
     */
    @Test
    public void findByNameNotExistOk(){
        final String randomString = this.factory.getRandomAsciiString(10);

        final Account account = this.accountRepository.findByName(randomString);

        Assertions.assertNull(account);
    }

}