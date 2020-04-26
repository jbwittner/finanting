package fr.finanting.server.it.repositories.accounttype;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.model.AccountType;

/**
 * Class to check the findAll method of AccountTypeRepository
 */
public class FindAllTest extends MotherAccountTypeRepositoryTest {

    /**
     * Test to check the findAll method
     */
    @Test
    public void findAllOk() {
        final Iterable<AccountType> accountTypeIterable = this.accountTypeRepository.findAll();
        final List<AccountType> resultsList = new ArrayList<>();
        accountTypeIterable.forEach(resultsList::add);

        Assertions.assertEquals(this.accountTypeList.size(), resultsList.size());

        Boolean listContains;

        for (final AccountType accountType : this.accountTypeList) {
            listContains = resultsList.contains(accountType);
            Assertions.assertTrue(listContains);
        }
    }

}