package fr.finanting.server;

import fr.finanting.server.model.AccountType;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory to help during tests
 */
public class TestObjectFactory {

    public static final int LENGTH_DEVICE_TYPE_MODEL = 10;
    public static final int LENGTH_DEVICE_SERIAL = 10;

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
     * Generate a random device type
     * @return Device type generated
     */
    public AccountType createRandomAccountType(){
        final String type = this.getRandomAsciiString(LENGTH_DEVICE_TYPE_MODEL);

        return this.createAccountType(type);
    }

    /**
     * Generate a device type
     * @param type String of type
     * @return Device type
     */
    public AccountType createAccountType(final String type){
        return new AccountType(type);
    }
}