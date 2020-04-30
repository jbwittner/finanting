package fr.finanting.server;

import fr.finanting.server.model.Account;
import fr.finanting.server.model.AccountType;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory to help during tests
 */
public class TestObjectFactory {

    public static final int LENGTH_ACCOUNT_TYPE = 10;
    public static final int LENGTH_ACCOUNT = 10;

    private List<String> listRandomString = new ArrayList<>();

    /**
     * Method de call when we need to reset the list of random string
     */
    public void resetListString(){
        this.listRandomString = new ArrayList<>();
    }

    /**
     * Generate a random ascii string.
     * When a new string is generated.
     * It is stored on a list used to check that the same chain is not obtained after reuse
     * @param length Length of the string
     * @return String generated
     */
    public String getRandomAsciiString(final int length){
        boolean isNotUnique = true;
        String randomString = "";

        while (isNotUnique){
            randomString = RandomStringUtils.randomAscii(length);
            isNotUnique = listRandomString.contains(randomString);
        }

        listRandomString.add(randomString);

        return randomString;
    }

    public Integer getRandomInteger(Integer min, Integer max){
        Integer result = (int) (min + (Math.random() * (max - min)));
        return result;
    }

    /**
     * Generate a random account type
     * @return Account type generated
     */
    public AccountType createAccountType(){
        final String type = this.getRandomAsciiString(LENGTH_ACCOUNT_TYPE);

        return this.createAccountType(type);
    }

    public Account createAccount(){
        final String name = this.getRandomAsciiString(LENGTH_ACCOUNT);
        final AccountType accountType = this.createAccountType();
        return this.createAccount(name, accountType);
    }

    public Account createAccount(final String name){
        final AccountType accountType = this.createAccountType();
        return this.createAccount(name, accountType);
    }

    public Account createAccount(final AccountType accountType){
        final String name = this.getRandomAsciiString(LENGTH_ACCOUNT);
        return this.createAccount(name, accountType);
    }

    /**
     * Generate a account type
     * @param type String of type
     * @return Account type
     */
    public AccountType createAccountType(final String type){
        return new AccountType(type);
    }

    public Account createAccount(final String name, final AccountType accountType){
        return new Account(name, accountType);
    }
}