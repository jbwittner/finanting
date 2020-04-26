package fr.finanting.server;

import fr.finanting.server.model.AccountType;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory to help during tests
 */
public class TestObjectFactory {

    public static final int LENGTH_ACCOUNT_TYPE = 10;

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

    /**
     * Generate a random account type
     * @return Account type generated
     */
    public AccountType createRandomAccountType(){
        final String type = this.getRandomAsciiString(LENGTH_ACCOUNT_TYPE);

        return this.createAccountType(type);
    }

    /**
     * Generate a account type
     * @param type String of type
     * @return Account type
     */
    public AccountType createAccountType(final String type){
        return new AccountType(type);
    }
}