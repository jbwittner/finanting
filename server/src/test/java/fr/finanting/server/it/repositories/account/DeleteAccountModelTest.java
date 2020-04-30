package fr.finanting.server.it.repositories.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteAccountModelTest extends MotherAccountRepositoryTest {

    @Test
    public void deleteOneAccountTypeOk(){

        final Long numberAccountBefore = this.accountRepository.count();

        this.accountRepository.delete(this.randomAccount);

        this.accountRepository.flush();

        final Long numberAccountAfter = this.accountRepository.count();

        Assertions.assertEquals(numberAccountBefore, numberAccountAfter + 1);

    }

    @Test
    public void deleteAllAccountTypeOk(){

        this.accountRepository.deleteAll();

        this.accountRepository.flush();

        final Long numberAccountAfter = this.accountRepository.count();

        Assertions.assertEquals(0, numberAccountAfter);

    }

}