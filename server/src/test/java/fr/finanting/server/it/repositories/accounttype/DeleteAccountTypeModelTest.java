package fr.finanting.server.it.repositories.accounttype;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import fr.finanting.server.model.Account;
import fr.finanting.server.repositorie.AccountRepository;

public class DeleteAccountTypeModelTest extends MotherAccountTypeRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void deleteOneAccountTypeOk(){

        final Long numberAccountTypeBefore = this.accountTypeRepository.count();

        this.accountTypeRepository.delete(this.randomAccountType);

        this.accountTypeRepository.flush();

        final Long numberAccountTypeAfter = this.accountTypeRepository.count();

        Assertions.assertEquals(numberAccountTypeBefore, numberAccountTypeAfter + 1);

    }

    @Test
    public void deleteAllAccountTypeOk(){

        this.accountTypeRepository.deleteAll();

        this.accountTypeRepository.flush();

        final Long numberAccountTypeAfter = this.accountTypeRepository.count();

        Assertions.assertEquals(0, numberAccountTypeAfter);

    }

    @Test
    public void deleteOneAccountTypeWithAccountNOk(){

        Account account = this.factory.createAccount(this.randomAccountType);

        accountRepository.save(account);

        this.accountTypeRepository.delete(this.randomAccountType);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            this.accountTypeRepository.flush();
        });

    }

    @Test
    public void deleteAllAccountWithOneAccountTypeWithAccountNOk(){

        Account account = this.factory.createAccount(this.randomAccountType);

        accountRepository.save(account);

        this.accountTypeRepository.deleteAll();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            this.accountTypeRepository.flush();
        });

    }

}