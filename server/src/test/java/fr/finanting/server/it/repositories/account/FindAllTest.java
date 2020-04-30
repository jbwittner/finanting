package fr.finanting.server.it.repositories.account;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.model.Account;

/**
 * Class to check the findAll method of AccountTypeRepository
 */
public class FindAllTest extends MotherAccountRepositoryTest {

    /**
     * Test to check the findAll method
     */
    @Test
    public void findAllOk() {
        final Iterable<Account> accountIterable = this.accountRepository.findAll();
        final List<Account> resultsList = new ArrayList<>();
        accountIterable.forEach(resultsList::add);

        Assertions.assertEquals(this.accountList.size(), resultsList.size());

        Boolean listContains;

        for (final Account account : this.accountList) {
            listContains = resultsList.contains(account);
            Assertions.assertTrue(listContains);
        }
    }

}