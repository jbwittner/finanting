package fr.finanting.server.testhelper;

import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.CategoryType;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.model.embeddable.Contact;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import fr.finanting.server.model.Group;
import fr.finanting.server.model.Role;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

/**
 * Factory to help during tests
 */
public class TestObjectFactory {

    public static final Integer NUMBER_MAX = 20_000_000;

    public static final int LENGTH_STANDARD = 30;
    public static final int LENGTH_URI = 15;
    public static final int LENGTH_EMAIL_NAME = 10;
    public static final int LENGTH_DOMAIN = 10;

    private List<String> listRandomString = new ArrayList<>();
    private List<String> listRandomEmail = new ArrayList<>();
    private List<Integer> listRandomNumber = new ArrayList<>();
    private List<String> listRandomName = new ArrayList<>();

    protected final Faker faker = new Faker();
    
    public void resetAllList(){
        
        this.listRandomString = new ArrayList<>();
        this.listRandomNumber = new ArrayList<>();
        this.listRandomEmail = new ArrayList<>();
        this.listRandomName = new ArrayList<>();
    }

    public Boolean getRandomBoolean(){

        return this.faker.random().nextBoolean();
    }

    public String getUniqueRandomAlphanumericString(final int length){

        boolean isNotUnique = true;
        String randomString = "";

        while (isNotUnique){
            randomString = RandomStringUtils.randomAlphanumeric(length);
            isNotUnique = listRandomString.contains(randomString);
        }

        listRandomString.add(randomString);

        return randomString;
    }

    public String getUniqueRandomAlphanumericString(){

        boolean isNotUnique = true;
        String randomString = "";

        while (isNotUnique){
            randomString = this.getRandomAlphanumericString();
            isNotUnique = listRandomString.contains(randomString);
        }

        listRandomString.add(randomString);

        return randomString;
    }

    public String getRandomAlphanumericString(final int length){

        return RandomStringUtils.randomAlphanumeric(length);
    }

    public String getRandomAlphanumericString(){

        return RandomStringUtils.randomAlphanumeric(LENGTH_STANDARD);
    }

    public String getUniqueRandomURI(){

        boolean isNotUnique = true;
        String randomUri = "";

        while (isNotUnique){
            randomUri = this.faker.internet().url();
            isNotUnique = listRandomString.contains(randomUri);
        }

        listRandomString.add(randomUri);

        return randomUri;
    }

    public Name getUniqueRandomName(){

        boolean isNotUnique = true;
        Name randomName = this.faker.name();

        while (isNotUnique){
            randomName = this.faker.name();
            isNotUnique = listRandomName.contains(randomName.username());
        }

        listRandomName.add(randomName.username());

        return randomName;
    }

    public String getUniqueRandomEmail(){
        boolean isNotUnique = true;
        String email = "";

        while (isNotUnique){
            email = this.faker.internet().emailAddress();
            isNotUnique = listRandomEmail.contains(email);
        }

        listRandomEmail.add(email);

        return email;

    }

    public Integer getUniqueRandomInteger(final Integer min, final Integer max){
        boolean isNotUnique = true;
        Integer randomNumber = 0;

        while (isNotUnique){
            randomNumber = this.getRandomInteger(min, max);
            isNotUnique = listRandomNumber.contains(randomNumber);
        }

        listRandomNumber.add(randomNumber);

        return randomNumber;
    }

    public Integer getUniqueRandomInteger(final Integer max){
        boolean isNotUnique = true;
        Integer randomNumber = 0;

        while (isNotUnique){
            randomNumber = this.getRandomInteger(max);
            isNotUnique = listRandomNumber.contains(randomNumber);
        }

        listRandomNumber.add(randomNumber);

        return randomNumber;
    }

    public Integer getUniqueRandomInteger(){
        return this.getUniqueRandomInteger(NUMBER_MAX);
    }

    public int getRandomInteger(final Integer max){
        final double random = Math.random() * max;
        return (int) random;
    }

    public long getRandomLong(final Integer max){
        final double random = Math.random() * max;
        return (long) random;
    }

    public Integer getRandomInteger(){
        return this.getRandomInteger(NUMBER_MAX);
    }

    public Long getRandomLong(){
        return this.getRandomLong(NUMBER_MAX);
    }

    public Integer getRandomInteger(final Integer min, final Integer max){
        return (int) (min + (Math.random() * (max - min)));
    }

    public User getUser(){
        final User user = new User();
        user.setEmail(this.faker.internet().emailAddress());
        final String firstName = StringUtils.capitalize(this.faker.name().firstName().toLowerCase());
        user.setFirstName(firstName);
        user.setLastName(this.faker.name().lastName().toUpperCase());
        user.setUserName(this.faker.name().username().toLowerCase());
        user.setPassword(this.getRandomAlphanumericString());
        
        final List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        return user;
    }

    /**
     * Method to get a new group
     */
    public Group getGroup(){
        final Group group = new Group();
        group.setGroupName(this.faker.company().name());
        final User user = this.getUser();
        group.setUserAdmin(user);
        final List<User> users = new ArrayList<>();
        users.add(user);
        group.setUsers(users);
        return group;
    }

    private Address getAddress(){
        final com.github.javafaker.Address addressFaker = this.faker.address();
        final Address address = new Address();
        address.setCity(addressFaker.city());
        address.setStreet(addressFaker.streetAddress());
        address.setZipCode(addressFaker.zipCode());
        return address;
    }

    private BankDetails getBankDetails(){
        final BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(this.getRandomAlphanumericString());
        bankDetails.setIban(this.getRandomAlphanumericString());
        bankDetails.setBankName(this.getRandomAlphanumericString());
        return bankDetails;
    }

    private BankingAccount getBankingAccount(final User user, final Group group){
        final BankingAccount bankingAccount = new BankingAccount();

        bankingAccount.setAddress(this.getAddress());
        bankingAccount.setBankDetails(this.getBankDetails());

        bankingAccount.setAbbreviation(this.getRandomAlphanumericString(6).toUpperCase());
        bankingAccount.setInitialBalance(0);
        bankingAccount.setLabel(this.getRandomAlphanumericString());

        bankingAccount.setGroup(group);
        bankingAccount.setUser(user);

        return bankingAccount;
    }

    public BankingAccount getBankingAccount(final User user){
        return this.getBankingAccount(user, null);
    }

    public BankingAccount getBankingAccount(final Group group){
        return this.getBankingAccount(null, group);
    }

    private Category getCategory(final User user, final Group group, final boolean isExpense){
        final Category category = new Category();

        category.setAbbreviation(this.getRandomAlphanumericString(6).toUpperCase());
        CategoryType categoryType;

        if(isExpense){
            categoryType = CategoryType.EXPENSE;
        } else {
            categoryType = CategoryType.REVENUE;
        }

        category.setCategoryType(categoryType);
        category.setDescritpion(this.faker.superhero().descriptor());
        category.setLabel(this.faker.company().catchPhrase());
        category.setGroup(group);
        category.setUser(user);

        return category;
    }

    public Category getCategory(final User user, final boolean isExpense){
        return this.getCategory(user, null, isExpense);
    }

    public Category getCategory(final Group group, final boolean isExpense){
        return this.getCategory(null, group, isExpense);
    }

    private Classification getClassification(final User user, final Group group){

        final Classification classification = new Classification();

        classification.setAbbreviation(this.getRandomAlphanumericString(6).toUpperCase());
        classification.setDescritpion(this.faker.superhero().descriptor());
        classification.setLabel(this.faker.company().catchPhrase());
        classification.setUser(user);
        classification.setGroup(group);

        return classification;

    }

    public Classification getClassification(final User user){
        return this.getClassification(user, null);
    }

    public Classification getClassification(final Group group){
        return this.getClassification(null, group);
    }

    private Contact getContact(){
        final Contact contact = new Contact();
        contact.setEmail(this.faker.internet().emailAddress());
        contact.setHomePhone(this.faker.phoneNumber().phoneNumber());
        contact.setPortablePhone(this.faker.phoneNumber().cellPhone());
        contact.setWebsite(this.faker.internet().url());
        return contact;
    }

    private Third getThird(final User user, final Group group){
        Third third = new Third();

        third.setAbbreviation(this.getRandomAlphanumericString(5).toUpperCase());
        third.setDescritpion(this.faker.superhero().descriptor());
        third.setLabel(this.faker.company().name());
        third.setAddress(this.getAddress());
        third.setBankDetails(this.getBankDetails());
        third.setContact(this.getContact());

        third.setUser(user);
        third.setGroup(group);

        return third;
    }

    public Third getThird(final User user){
        return this.getThird(user, null);
    }

    public Third getThird(final Group group){
        return this.getThird(null, group);
    }

}