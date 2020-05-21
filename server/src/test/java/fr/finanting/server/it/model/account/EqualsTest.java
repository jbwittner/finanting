package fr.finanting.server.it.model.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.finanting.server.TestObjectFactory;
import fr.finanting.server.model.AccountType;

public class EqualsTest{

    AccountType accountTypeOne;
    AccountType accountTypeTwo;

    TestObjectFactory factory = new TestObjectFactory();

    @BeforeEach
    public void initDataBeforeEach() {
        this.accountTypeOne = this.factory.createAccountType();
        this.accountTypeOne.setId(this.factory.getUniqueRandomInteger());
        this.accountTypeTwo = this.factory.createAccountType();
        this.accountTypeTwo.setId(this.factory.getUniqueRandomInteger());
    }
    

    @Test
    public void createOk(){
        Assertions.assertNotEquals(accountTypeOne, accountTypeTwo);
    }

    @Test
    public void createOk1(){
        this.accountTypeTwo.setId(this.accountTypeOne.getId());
        Assertions.assertNotEquals(accountTypeOne, accountTypeTwo);
    }

    @Test
    public void createOk2(){
        this.accountTypeTwo.setType(this.accountTypeOne.getType());
        Assertions.assertNotEquals(accountTypeOne, accountTypeTwo);
    }

    @Test
    public void createOk3(){
        this.accountTypeTwo.setId(this.accountTypeOne.getId());
        this.accountTypeTwo.setType(this.accountTypeOne.getType());
        this.accountTypeOne.equals(this.accountTypeTwo);
        Assertions.assertEquals(accountTypeOne, accountTypeTwo);
    }
}