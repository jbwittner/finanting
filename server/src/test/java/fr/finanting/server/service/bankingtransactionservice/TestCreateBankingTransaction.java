package fr.finanting.server.service.bankingtransactionservice;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateBankingTransactionParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.BankingTransactionServiceImpl;

public class TestCreateBankingTransaction extends AbstractMotherIntegrationTest {

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;
    
    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private ThirdRepository thirdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    private BankingTransactionServiceImpl bankingTransactionServiceImpl;

    private CreateBankingTransactionParameter createGroupBankingTransactionParameter;
    private CreateBankingTransactionParameter createUserBankingTransactionParameter;

    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.bankingTransactionServiceImpl = new BankingTransactionServiceImpl(this.bankingTransactionRepository,
                                                                                this.bankingAccountRepository,
                                                                                this.thirdRepository,
                                                                                this.categoryRepository,
                                                                                this.classificationRepository,
                                                                                this.currencyRepository,
                                                                                this.userRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

        this.prepareUserData(user);
        this.prepareGroupData(group);

    }

    private void prepareUserData(User user){
        this.createUserBankingTransactionParameter = new CreateBankingTransactionParameter();

        BankingAccount userBankingAccount = this.testFactory.getBankingAccount(user);

        BankingAccount linkedUserBankingAccount = this.testFactory.getBankingAccount(user);
        linkedUserBankingAccount.setDefaultCurrency(userBankingAccount.getDefaultCurrency());

        Third third = this.testFactory.getThird(user);
        Category category = this.testFactory.getCategory(user, true);
        Classification classification = this.testFactory.getClassification(user);

        String description = this.faker.chuckNorris().fact();

        Date amountDate = new Date(System.currentTimeMillis());
        Date transactionDate = new Date(System.currentTimeMillis());

        Double amount = this.testFactory.getRandomDouble();
        Double currencyAmount = this.testFactory.getRandomDouble();

        this.createUserBankingTransactionParameter.setAccountId(userBankingAccount.getId());
        this.createUserBankingTransactionParameter.setLinkedAccountId(linkedUserBankingAccount.getId());
        this.createUserBankingTransactionParameter.setThirdId(third.getId());
        this.createUserBankingTransactionParameter.setCategoryId(category.getId());
        this.createUserBankingTransactionParameter.setClassificationId(classification.getId());
        this.createUserBankingTransactionParameter.setTransactionDate(transactionDate);
        this.createUserBankingTransactionParameter.setAmountDate(amountDate);
        this.createUserBankingTransactionParameter.setAmount(amount);
        this.createUserBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.createUserBankingTransactionParameter.setCurrencyId(userBankingAccount.getDefaultCurrency().getId());
        this.createUserBankingTransactionParameter.setDescription(description);

    }

    private void prepareGroupData(Group group){
        this.createGroupBankingTransactionParameter = new CreateBankingTransactionParameter();

        BankingAccount groupBankingAccount = this.testFactory.getBankingAccount(group);
        BankingAccount linkedGroupBankingAccount = this.testFactory.getBankingAccount(group);
        linkedGroupBankingAccount.setDefaultCurrency(groupBankingAccount.getDefaultCurrency());

        Third third = this.testFactory.getThird(group);
        Category category = this.testFactory.getCategory(group, true);
        Classification classification = this.testFactory.getClassification(group);

        String description = this.faker.chuckNorris().fact();

        Date amountDate = new Date(System.currentTimeMillis());
        Date transactionDate = new Date(System.currentTimeMillis());

        Double amount = this.testFactory.getRandomDouble();
        Double currencyAmount = this.testFactory.getRandomDouble();

        this.createGroupBankingTransactionParameter.setAccountId(groupBankingAccount.getId());
        this.createGroupBankingTransactionParameter.setLinkedAccountId(linkedGroupBankingAccount.getId());
        this.createGroupBankingTransactionParameter.setThirdId(third.getId());
        this.createGroupBankingTransactionParameter.setCategoryId(category.getId());
        this.createGroupBankingTransactionParameter.setClassificationId(classification.getId());
        this.createGroupBankingTransactionParameter.setTransactionDate(transactionDate);
        this.createGroupBankingTransactionParameter.setAmountDate(amountDate);
        this.createGroupBankingTransactionParameter.setAmount(amount);
        this.createGroupBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.createGroupBankingTransactionParameter.setCurrencyId(groupBankingAccount.getDefaultCurrency().getId());
        this.createGroupBankingTransactionParameter.setDescription(description);

    }

    private void checkData(final BankingTransactionDTO bankingTransactionDTO, final BankingTransaction bankingTransaction, final CreateBankingTransactionParameter parameter){
        
        Assertions.assertEquals(parameter.getAccountId(), bankingTransactionDTO.getBankingAccountDTO().getId());
        Assertions.assertEquals(parameter.getAccountId(), bankingTransaction.getAccount().getId());

        if(parameter.getLinkedAccountId() != null){
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransactionDTO.getLinkedBankingAccountDTO().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransaction.getLinkedAccount().getId());

            BankingTransaction mirrorTransaction = bankingTransaction.getMirrorTransaction();
            Assertions.assertEquals(parameter.getAccountId(), mirrorTransaction.getLinkedAccount().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), mirrorTransaction.getAccount().getId());

            Assertions.assertEquals(bankingTransaction.getAccount().getId(), mirrorTransaction.getLinkedAccount().getId());
            
            if(parameter.getThirdId() != null){
                Assertions.assertEquals(parameter.getThirdId(), mirrorTransaction.getThird().getId());
            } else {
                Assertions.assertNull(mirrorTransaction.getThird());
            }

            if(parameter.getCategoryId() != null){
                Assertions.assertEquals(parameter.getCategoryId(), mirrorTransaction.getCategory().getId());
            } else {
                Assertions.assertNull(mirrorTransaction.getCategory());
            }

            if(parameter.getClassificationId() != null){
                Assertions.assertEquals(parameter.getClassificationId(), mirrorTransaction.getClassification().getId());
            } else {
                Assertions.assertNull(mirrorTransaction.getClassification());
            }

            Assertions.assertEquals(parameter.getTransactionDate(), mirrorTransaction.getTransactionDate());
            Assertions.assertEquals(parameter.getAmountDate(), mirrorTransaction.getAmountDate());

            Double mirrorAmount;
            Double mirrorCurrencyAmount = parameter.getCurrencyAmount() * -1;

            if(!bankingTransaction.getLinkedAccount().getDefaultCurrency().equals(bankingTransaction.getAccount().getDefaultCurrency())){
                mirrorAmount = mirrorCurrencyAmount
                    * Double.valueOf(bankingTransaction.getAccount().getDefaultCurrency().getRate())
                    / Double.valueOf(bankingTransaction.getLinkedAccount().getDefaultCurrency().getRate());
            } else {
                mirrorAmount = bankingTransaction.getAmount() * -1;
            }


            Assertions.assertEquals(mirrorAmount, mirrorTransaction.getAmount());
            Assertions.assertEquals(mirrorCurrencyAmount, mirrorTransaction.getCurrencyAmount());

            Assertions.assertEquals(parameter.getCurrencyId(), mirrorTransaction.getCurrency().getId());
            Assertions.assertEquals(parameter.getDescription(), mirrorTransaction.getDescription());

        } else {
            Assertions.assertNull(bankingTransactionDTO.getLinkedBankingAccountDTO());
            Assertions.assertNull(bankingTransaction.getLinkedAccount());
            Assertions.assertNull(bankingTransaction.getMirrorTransaction());
        }

        if(parameter.getThirdId() != null){
            Assertions.assertEquals(parameter.getThirdId(), bankingTransactionDTO.getThirdDTO().getId());
            Assertions.assertEquals(parameter.getThirdId(), bankingTransaction.getThird().getId());
        } else {
            Assertions.assertNull(bankingTransactionDTO.getThirdDTO());
            Assertions.assertNull(bankingTransaction.getThird());
        }

        if(parameter.getCategoryId() != null){
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransactionDTO.getCategoryDTO().getId());
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransaction.getCategory().getId());
        } else {
            Assertions.assertNull(bankingTransactionDTO.getCategoryDTO());
            Assertions.assertNull(bankingTransaction.getCategory());
        }

        if(parameter.getClassificationId() != null){
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransactionDTO.getClassificationDTO().getId());
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransaction.getClassification().getId());
        } else {
            Assertions.assertNull(bankingTransactionDTO.getClassificationDTO());
            Assertions.assertNull(bankingTransaction.getClassification());
        }

        Assertions.assertEquals(parameter.getTransactionDate(), bankingTransactionDTO.getTransactionDate());
        Assertions.assertEquals(parameter.getTransactionDate(), bankingTransaction.getTransactionDate());

        Assertions.assertEquals(parameter.getAmountDate(), bankingTransactionDTO.getAmountDate());
        Assertions.assertEquals(parameter.getAmountDate(), bankingTransaction.getAmountDate());

        Assertions.assertEquals(parameter.getAmount(), bankingTransactionDTO.getAmount());
        Assertions.assertEquals(parameter.getAmount(), bankingTransaction.getAmount());

        Assertions.assertEquals(parameter.getCurrencyAmount(), bankingTransactionDTO.getCurrencyAmount());
        Assertions.assertEquals(parameter.getCurrencyAmount(), bankingTransaction.getCurrencyAmount());

        Assertions.assertEquals(parameter.getCurrencyId(), bankingTransactionDTO.getCurrencyDTO().getId());
        Assertions.assertEquals(parameter.getCurrencyId(), bankingTransaction.getCurrency().getId());

        Assertions.assertEquals(parameter.getDescription(), bankingTransactionDTO.getDescription());
        Assertions.assertEquals(parameter.getDescription(), bankingTransaction.getDescription());

    }

    @Test
    public void testCreateUserTransaction() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrency() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingAccount linkedBankingAccount = this.testFactory.getBankingAccount(user);
        this.createUserBankingTransactionParameter.setLinkedAccountId(linkedBankingAccount.getId());
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrencyWithSpecificValues() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Double amountCurrency = Double.valueOf(150);
        Double amount = Double.valueOf(15);
        Integer rateAccountCurrency = 10;
        Integer rateLinkedAccountCurrency = 20;
        Double expectedMirrorAmount = Double.valueOf(-75);
        Double expectedMirrorAmountCurrency = amountCurrency * -1;
        
        BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);
        Currency currency = bankingAccount.getDefaultCurrency();
        currency.setRate(rateAccountCurrency);
        bankingAccount.setDefaultCurrency(currency);

        BankingAccount linkedBankingAccount = this.testFactory.getBankingAccount(user);
        Currency linkedCurrency = linkedBankingAccount.getDefaultCurrency();
        linkedCurrency.setRate(rateLinkedAccountCurrency);
        linkedBankingAccount.setDefaultCurrency(linkedCurrency);

        this.createUserBankingTransactionParameter.setAccountId(bankingAccount.getId());
        this.createUserBankingTransactionParameter.setLinkedAccountId(linkedBankingAccount.getId());

        this.createUserBankingTransactionParameter.setAmount(amount);
        this.createUserBankingTransactionParameter.setCurrencyAmount(amountCurrency);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);

        Assertions.assertEquals(amount, bankingTransaction.getAmount());
        Assertions.assertEquals(amountCurrency, bankingTransaction.getCurrencyAmount());
        Assertions.assertEquals(expectedMirrorAmount, bankingTransaction.getMirrorTransaction().getAmount());
        Assertions.assertEquals(expectedMirrorAmountCurrency, bankingTransaction.getMirrorTransaction().getCurrencyAmount());

    }

    @Test
    public void testCreateGroupTransaction() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createGroupBankingTransactionParameter);
    }


    @Test
    public void testCreateUserWithoutLinkedAccount() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setLinkedAccountId(null);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutThird() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setThirdId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutCategory() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setCategoryId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutClassification() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setClassificationId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithBankingAccountNotExistException() {
        this.createUserBankingTransactionParameter.setAccountId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedBankingAccountNotExistException() {
        this.createUserBankingTransactionParameter.setLinkedAccountId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithThirdNotExistException() {
        this.createUserBankingTransactionParameter.setThirdId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCategoryNotExistException() {
        this.createUserBankingTransactionParameter.setCategoryId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithClassificationNotExistException() {
        this.createUserBankingTransactionParameter.setClassificationId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCurrencyNotExistException() {
        this.createUserBankingTransactionParameter.setCurrencyId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.testFactory.getUser();
        BankingAccount otherBankingAccount = this.testFactory.getBankingAccount(otherUser);

        this.createUserBankingTransactionParameter.setAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.testFactory.getUser();
        BankingAccount otherBankingAccount = this.testFactory.getBankingAccount(otherUser);

        this.createUserBankingTransactionParameter.setLinkedAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithUserAccountAndGroupLinkedBankingTransactionBankingAccountException() {
        BankingAccount groupBankingAccount = this.testFactory.getBankingAccount(this.group);

        this.createUserBankingTransactionParameter.setLinkedAccountId(groupBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithGroupAccountAndUserLinkedBankingTransactionBankingAccountException() {
        BankingAccount userBankingAccount = this.testFactory.getBankingAccount(this.user);

        this.createGroupBankingTransactionParameter.setLinkedAccountId(userBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithGroupAccountAndOtherGroupLinkedBankingTransactionBankingAccountException() {
        BankingAccount userBankingAccount = this.testFactory.getBankingAccount(this.user);

        this.createGroupBankingTransactionParameter.setLinkedAccountId(userBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithGroupAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, otherUser.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupThird() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer thirdId = this.createGroupBankingTransactionParameter.getThirdId();
        this.createUserBankingTransactionParameter.setThirdId(thirdId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupCategory() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer categoryId = this.createGroupBankingTransactionParameter.getCategoryId();
        this.createUserBankingTransactionParameter.setCategoryId(categoryId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupClassification() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer classificationId = this.createGroupBankingTransactionParameter.getClassificationId();
        this.createUserBankingTransactionParameter.setClassificationId(classificationId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserThird() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer thirdId = this.createUserBankingTransactionParameter.getThirdId();
        this.createGroupBankingTransactionParameter.setThirdId(thirdId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserCategory() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer categoryId = this.createUserBankingTransactionParameter.getCategoryId();
        this.createGroupBankingTransactionParameter.setCategoryId(categoryId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserClassification() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException{
        Integer classificationId = this.createUserBankingTransactionParameter.getClassificationId();
        this.createGroupBankingTransactionParameter.setClassificationId(classificationId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName()));
    }


}
