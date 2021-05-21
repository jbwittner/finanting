package fr.finanting.server.service.bankingtransactionservice;

import java.util.Date;

import fr.finanting.server.codegen.model.BankingTransactionDTO;
import fr.finanting.server.codegen.model.BankingTransactionParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NotUserElementException;
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

    private BankingTransactionParameter groupBankingTransactionParameter;
    private BankingTransactionParameter userBankingTransactionParameter;

    private Group group;
    private User user;

    @Override
    protected void initDataBeforeEach() {
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

    private void prepareUserData(final User user){
        this.userBankingTransactionParameter = new BankingTransactionParameter();

        final BankingAccount userBankingAccount = this.testFactory.getBankingAccount(user);

        final BankingAccount linkedUserBankingAccount = this.testFactory.getBankingAccount(user);
        linkedUserBankingAccount.setDefaultCurrency(userBankingAccount.getDefaultCurrency());

        final Third third = this.testFactory.getThird(user);
        final Category category = this.testFactory.getCategory(user, true);
        final Classification classification = this.testFactory.getClassification(user);

        final String description = this.faker.chuckNorris().fact();

        final Date amountDate = new Date(System.currentTimeMillis());
        final Date transactionDate = new Date(System.currentTimeMillis());

        final Double amount = this.testFactory.getRandomDouble();
        final Double currencyAmount = this.testFactory.getRandomDouble();

        this.userBankingTransactionParameter.setAccountId(userBankingAccount.getId());
        this.userBankingTransactionParameter.setLinkedAccountId(linkedUserBankingAccount.getId());
        this.userBankingTransactionParameter.setThirdId(third.getId());
        this.userBankingTransactionParameter.setCategoryId(category.getId());
        this.userBankingTransactionParameter.setClassificationId(classification.getId());
        this.userBankingTransactionParameter.setTransactionDate(transactionDate);
        this.userBankingTransactionParameter.setAmountDate(amountDate);
        this.userBankingTransactionParameter.setAmount(amount);
        this.userBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.userBankingTransactionParameter.setCurrencyId(userBankingAccount.getDefaultCurrency().getId());
        this.userBankingTransactionParameter.setDescription(description);

    }

    private void prepareGroupData(final Group group){
        this.groupBankingTransactionParameter = new BankingTransactionParameter();

        final BankingAccount groupBankingAccount = this.testFactory.getBankingAccount(group);
        final BankingAccount linkedGroupBankingAccount = this.testFactory.getBankingAccount(group);
        linkedGroupBankingAccount.setDefaultCurrency(groupBankingAccount.getDefaultCurrency());

        final Third third = this.testFactory.getThird(group);
        final Category category = this.testFactory.getCategory(group, true);
        final Classification classification = this.testFactory.getClassification(group);

        final String description = this.faker.chuckNorris().fact();

        final Date amountDate = new Date(System.currentTimeMillis());
        final Date transactionDate = new Date(System.currentTimeMillis());

        final Double amount = this.testFactory.getRandomDouble();
        final Double currencyAmount = this.testFactory.getRandomDouble();

        this.groupBankingTransactionParameter.setAccountId(groupBankingAccount.getId());
        this.groupBankingTransactionParameter.setLinkedAccountId(linkedGroupBankingAccount.getId());
        this.groupBankingTransactionParameter.setThirdId(third.getId());
        this.groupBankingTransactionParameter.setCategoryId(category.getId());
        this.groupBankingTransactionParameter.setClassificationId(classification.getId());
        this.groupBankingTransactionParameter.setTransactionDate(transactionDate);
        this.groupBankingTransactionParameter.setAmountDate(amountDate);
        this.groupBankingTransactionParameter.setAmount(amount);
        this.groupBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.groupBankingTransactionParameter.setCurrencyId(groupBankingAccount.getDefaultCurrency().getId());
        this.groupBankingTransactionParameter.setDescription(description);

    }

    private void checkData(final BankingTransactionDTO bankingTransactionDTO, final BankingTransaction bankingTransaction, final BankingTransactionParameter parameter){
        
        Assertions.assertEquals(parameter.getAccountId(), bankingTransactionDTO.getBankingAccountDTO().getId());
        Assertions.assertEquals(parameter.getAccountId(), bankingTransaction.getAccount().getId());

        if(parameter.getLinkedAccountId() == null){
            Assertions.assertNull(bankingTransactionDTO.getLinkedBankingAccountDTO());
            Assertions.assertNull(bankingTransaction.getLinkedAccount());
            Assertions.assertNull(bankingTransaction.getMirrorTransaction());
        } else {
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransactionDTO.getLinkedBankingAccountDTO().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), bankingTransaction.getLinkedAccount().getId());

            final BankingTransaction mirrorTransaction = bankingTransaction.getMirrorTransaction();
            Assertions.assertEquals(parameter.getAccountId(), mirrorTransaction.getLinkedAccount().getId());
            Assertions.assertEquals(parameter.getLinkedAccountId(), mirrorTransaction.getAccount().getId());

            Assertions.assertEquals(bankingTransaction.getAccount().getId(), mirrorTransaction.getLinkedAccount().getId());
            
            if(parameter.getThirdId() == null){
                Assertions.assertNull(mirrorTransaction.getThird());
            } else {
                Assertions.assertEquals(parameter.getThirdId(), mirrorTransaction.getThird().getId());
            }

            if(parameter.getCategoryId() == null){
                Assertions.assertNull(mirrorTransaction.getCategory());
            } else {
                Assertions.assertEquals(parameter.getCategoryId(), mirrorTransaction.getCategory().getId());
            }

            if(parameter.getClassificationId() == null){
                Assertions.assertNull(mirrorTransaction.getClassification());
            } else {
                Assertions.assertEquals(parameter.getClassificationId(), mirrorTransaction.getClassification().getId());
            }

            Assertions.assertEquals(parameter.getTransactionDate(), mirrorTransaction.getTransactionDate());
            Assertions.assertEquals(parameter.getAmountDate(), mirrorTransaction.getAmountDate());

            Double mirrorAmount;
            final Double mirrorCurrencyAmount = parameter.getCurrencyAmount() * -1;

            if(bankingTransaction.getLinkedAccount().getDefaultCurrency().equals(bankingTransaction.getAccount().getDefaultCurrency())){
                mirrorAmount = bankingTransaction.getAmount() * -1;
            } else {
                mirrorAmount = mirrorCurrencyAmount
                    * Double.valueOf(bankingTransaction.getAccount().getDefaultCurrency().getRate())
                    / Double.valueOf(bankingTransaction.getLinkedAccount().getDefaultCurrency().getRate());
            }

            Assertions.assertEquals(mirrorAmount, mirrorTransaction.getAmount());
            Assertions.assertEquals(mirrorCurrencyAmount, mirrorTransaction.getCurrencyAmount());

            Assertions.assertEquals(parameter.getCurrencyId(), mirrorTransaction.getCurrency().getId());
            Assertions.assertEquals(parameter.getDescription(), mirrorTransaction.getDescription());

        }

        if(parameter.getThirdId() == null){
            Assertions.assertNull(bankingTransactionDTO.getThirdDTO());
            Assertions.assertNull(bankingTransaction.getThird());
        } else {
            Assertions.assertEquals(parameter.getThirdId(), bankingTransactionDTO.getThirdDTO().getId());
            Assertions.assertEquals(parameter.getThirdId(), bankingTransaction.getThird().getId());
        }

        if(parameter.getCategoryId() == null){
            Assertions.assertNull(bankingTransactionDTO.getCategoryDTO());
            Assertions.assertNull(bankingTransaction.getCategory());
        } else {
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransactionDTO.getCategoryDTO().getId());
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransaction.getCategory().getId());
        }

        if(parameter.getClassificationId() == null){
            Assertions.assertNull(bankingTransactionDTO.getClassificationDTO());
            Assertions.assertNull(bankingTransaction.getClassification());
        } else {
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransactionDTO.getClassificationDTO().getId());
            Assertions.assertEquals(parameter.getClassificationId(), bankingTransaction.getClassification().getId());
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
    public void testCreateUserTransaction()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrency()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
                
        final BankingAccount linkedBankingAccount = this.testFactory.getBankingAccount(user);
        this.userBankingTransactionParameter.setLinkedAccountId(linkedBankingAccount.getId());
        
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrencyWithSpecificValues()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
            ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException,
            CurrencyNotExistException, NotUserElementException{
        
        final Double amountCurrency = 150.0;
        final Double amount = 15.0;
        final Integer rateAccountCurrency = 10;
        final Integer rateLinkedAccountCurrency = 20;
        final Double expectedMirrorAmount = (double) -75;
        final Double expectedMirrorAmountCurrency = amountCurrency * -1;
        
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(user);
        final Currency currency = bankingAccount.getDefaultCurrency();
        currency.setRate(rateAccountCurrency);
        bankingAccount.setDefaultCurrency(currency);

        final BankingAccount linkedBankingAccount = this.testFactory.getBankingAccount(user);
        final Currency linkedCurrency = linkedBankingAccount.getDefaultCurrency();
        linkedCurrency.setRate(rateLinkedAccountCurrency);
        linkedBankingAccount.setDefaultCurrency(linkedCurrency);

        this.userBankingTransactionParameter.setAccountId(bankingAccount.getId());
        this.userBankingTransactionParameter.setLinkedAccountId(linkedBankingAccount.getId());

        this.userBankingTransactionParameter.setAmount(amount);
        this.userBankingTransactionParameter.setCurrencyAmount(amountCurrency);
        
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);

        Assertions.assertEquals(amount, bankingTransaction.getAmount());
        Assertions.assertEquals(amountCurrency, bankingTransaction.getCurrencyAmount());
        Assertions.assertEquals(expectedMirrorAmount, bankingTransaction.getMirrorTransaction().getAmount());
        Assertions.assertEquals(expectedMirrorAmountCurrency, bankingTransaction.getMirrorTransaction().getCurrencyAmount());

    }

    @Test
    public void testCreateGroupTransaction()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException,
            ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException,
            CurrencyNotExistException, NotUserElementException{
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.groupBankingTransactionParameter);
    }


    @Test
    public void testCreateUserWithoutLinkedAccount()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
                        this.userBankingTransactionParameter.setLinkedAccountId(null);

        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutThird()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        this.userBankingTransactionParameter.setThirdId(null);
        
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutCategory()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        this.userBankingTransactionParameter.setCategoryId(null);
        
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutClassification()
        throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException,
            CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        this.userBankingTransactionParameter.setClassificationId(null);
        
        final BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName());
        final BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.userBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithBankingAccountNotExistException() {
        this.userBankingTransactionParameter.setAccountId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedBankingAccountNotExistException() {
        this.userBankingTransactionParameter.setLinkedAccountId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithThirdNotExistException() {
        this.userBankingTransactionParameter.setThirdId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCategoryNotExistException() {
        this.userBankingTransactionParameter.setCategoryId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithClassificationNotExistException() {
        this.userBankingTransactionParameter.setClassificationId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCurrencyNotExistException() {
        this.userBankingTransactionParameter.setCurrencyId(this.testFactory.getRandomInteger());
        
        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithAccountBadAssociationBankingTransactionBankingAccountException() {
        final User otherUser = this.testFactory.getUser();
        final BankingAccount otherBankingAccount = this.testFactory.getBankingAccount(otherUser);

        this.userBankingTransactionParameter.setAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(NotUserElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedAccountBadAssociationBankingTransactionBankingAccountException() {
        final User otherUser = this.testFactory.getUser();
        final BankingAccount otherBankingAccount = this.testFactory.getBankingAccount(otherUser);

        this.userBankingTransactionParameter.setLinkedAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(NotUserElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithUserAccountAndGroupLinkedBankingTransactionBankingAccountException() {
        final BankingAccount groupBankingAccount = this.testFactory.getBankingAccount(this.group);

        this.userBankingTransactionParameter.setLinkedAccountId(groupBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithGroupAccountAndUserLinkedBankingTransactionBankingAccountException() {
        final BankingAccount userBankingAccount = this.testFactory.getBankingAccount(this.user);

        this.groupBankingTransactionParameter.setLinkedAccountId(userBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateTransactionWithGroupAccountAndOtherGroupLinkedBankingTransactionBankingAccountException() {
        final BankingAccount userBankingAccount = this.testFactory.getBankingAccount(this.user);

        this.groupBankingTransactionParameter.setLinkedAccountId(userBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithGroupAccountBadAssociationBankingTransactionBankingAccountException() {
        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, otherUser.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupThird() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer thirdId = this.groupBankingTransactionParameter.getThirdId();
        this.userBankingTransactionParameter.setThirdId(thirdId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupCategory() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer categoryId = this.groupBankingTransactionParameter.getCategoryId();
        this.userBankingTransactionParameter.setCategoryId(categoryId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserTransactionwithGroupClassification() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer classificationId = this.groupBankingTransactionParameter.getClassificationId();
        this.userBankingTransactionParameter.setClassificationId(classificationId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.userBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserThird() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer thirdId = this.userBankingTransactionParameter.getThirdId();
        this.groupBankingTransactionParameter.setThirdId(thirdId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserCategory() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer categoryId = this.userBankingTransactionParameter.getCategoryId();
        this.groupBankingTransactionParameter.setCategoryId(categoryId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateGroupTransactionwithUserClassification() throws BankingAccountNotExistException, BadAssociationElementException, UserNotInGroupException, ThirdNotExistException, CategoryNotExistException, ClassificationNotExistException, CurrencyNotExistException, NotUserElementException{
        final Integer classificationId = this.userBankingTransactionParameter.getClassificationId();
        this.groupBankingTransactionParameter.setClassificationId(classificationId);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.groupBankingTransactionParameter, this.user.getUserName()));
    }


}
