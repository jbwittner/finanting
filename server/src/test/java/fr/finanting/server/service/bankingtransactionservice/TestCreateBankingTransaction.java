package fr.finanting.server.service.bankingtransactionservice;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import fr.finanting.server.dto.BankingTransactionDTO;
import fr.finanting.server.exception.BadAssociationBankingTransactionBankingAccountException;
import fr.finanting.server.exception.BankingAccountNotExistException;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.ThirdNoUserException;
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
import fr.finanting.server.repository.GroupRepository;
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

    @Autowired
    private GroupRepository groupRepository;

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

        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);
        
        this.prepareUserData(user);
        this.prepareGroupData(group);

    }

    private void prepareUserData(User user){
        this.createUserBankingTransactionParameter = new CreateBankingTransactionParameter();

        BankingAccount userBankingAccount = this.factory.getBankingAccount(user);
        Currency currency = this.currencyRepository.save(userBankingAccount.getDefaultCurrency());
        userBankingAccount.setDefaultCurrency(currency);
        userBankingAccount = this.bankingAccountRepository.save(userBankingAccount);

        BankingAccount linkedUserBankingAccount = this.factory.getBankingAccount(user);
        linkedUserBankingAccount.setDefaultCurrency(currency);
        linkedUserBankingAccount = this.bankingAccountRepository.save(linkedUserBankingAccount);

        Third third = this.thirdRepository.save(this.factory.getThird(user));
        Category category = this.categoryRepository.save(this.factory.getCategory(user, true));
        Classification classification = this.classificationRepository.save(this.factory.getClassification(user));

        String description = this.faker.chuckNorris().fact();

        Date amountDate = new Date(System.currentTimeMillis());
        Date transactionDate = new Date(System.currentTimeMillis());

        Double amount = this.factory.getRandomDouble();
        Double currencyAmount = this.factory.getRandomDouble();

        this.createUserBankingTransactionParameter.setAccountId(userBankingAccount.getId());
        this.createUserBankingTransactionParameter.setLinkedAccountId(linkedUserBankingAccount.getId());
        this.createUserBankingTransactionParameter.setThirdId(third.getId());
        this.createUserBankingTransactionParameter.setCategoryId(category.getId());
        this.createUserBankingTransactionParameter.setClassificationId(classification.getId());
        this.createUserBankingTransactionParameter.setTransactionDate(transactionDate);
        this.createUserBankingTransactionParameter.setAmountDate(amountDate);
        this.createUserBankingTransactionParameter.setAmount(amount);
        this.createUserBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.createUserBankingTransactionParameter.setCurrencyId(currency.getId());
        this.createUserBankingTransactionParameter.setDescription(description);

    }

    private void prepareGroupData(Group group){
        this.createGroupBankingTransactionParameter = new CreateBankingTransactionParameter();

        BankingAccount groupBankingAccount = this.factory.getBankingAccount(group);
        Currency currency = this.currencyRepository.save(groupBankingAccount.getDefaultCurrency());
        groupBankingAccount.setDefaultCurrency(currency);
        groupBankingAccount = this.bankingAccountRepository.save(groupBankingAccount);

        BankingAccount linkedGroupBankingAccount = this.factory.getBankingAccount(group);
        linkedGroupBankingAccount.setDefaultCurrency(currency);
        linkedGroupBankingAccount = this.bankingAccountRepository.save(linkedGroupBankingAccount);

        Third third = this.thirdRepository.save(this.factory.getThird(group));
        Category category = this.categoryRepository.save(this.factory.getCategory(group, true));
        Classification classification = this.classificationRepository.save(this.factory.getClassification(group));

        String description = this.faker.chuckNorris().fact();

        Date amountDate = new Date(System.currentTimeMillis());
        Date transactionDate = new Date(System.currentTimeMillis());

        Double amount = this.factory.getRandomDouble();
        Double currencyAmount = this.factory.getRandomDouble();

        this.createGroupBankingTransactionParameter.setAccountId(groupBankingAccount.getId());
        this.createGroupBankingTransactionParameter.setLinkedAccountId(linkedGroupBankingAccount.getId());
        this.createGroupBankingTransactionParameter.setThirdId(third.getId());
        this.createGroupBankingTransactionParameter.setCategoryId(category.getId());
        this.createGroupBankingTransactionParameter.setClassificationId(classification.getId());
        this.createGroupBankingTransactionParameter.setTransactionDate(transactionDate);
        this.createGroupBankingTransactionParameter.setAmountDate(amountDate);
        this.createGroupBankingTransactionParameter.setAmount(amount);
        this.createGroupBankingTransactionParameter.setCurrencyAmount(currencyAmount);
        this.createGroupBankingTransactionParameter.setCurrencyId(currency.getId());
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
            
            if(parameter.getThirdId() != null){
                Assertions.assertEquals(parameter.getThirdId(), mirrorTransaction.getThird().getId());
            }

            if(parameter.getCategoryId() != null){
                Assertions.assertEquals(parameter.getCategoryId(), mirrorTransaction.getCategory().getId());
            }

            if(parameter.getClassificationId() != null){
                Assertions.assertEquals(parameter.getClassificationId(), mirrorTransaction.getClassification().getId());
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

        }

        if(parameter.getThirdId() != null){
            Assertions.assertEquals(parameter.getThirdId(), bankingTransactionDTO.getThirdDTO().getId());
            Assertions.assertEquals(parameter.getThirdId(), bankingTransaction.getThird().getId());
        }

        if(parameter.getCategoryId() != null){
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransactionDTO.getCategoryDTO().getId());
            Assertions.assertEquals(parameter.getCategoryId(), bankingTransaction.getCategory().getId());
        }


        if(parameter.getClassificationId() != null){
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
    public void testCreateUserTransaction() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrency() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        BankingAccount linkedBankingAccount = this.factory.getBankingAccount(user);
        Currency currency = this.currencyRepository.save(linkedBankingAccount.getDefaultCurrency());
        linkedBankingAccount.setDefaultCurrency(currency);
        linkedBankingAccount = this.bankingAccountRepository.save(linkedBankingAccount);

        this.createUserBankingTransactionParameter.setLinkedAccountId(linkedBankingAccount.getId());
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserTransactionWithLinkedAccountWithOtherDefaultCurrencyWithSpecificValues() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        Double amountCurrency = Double.valueOf(150);
        Double amount = Double.valueOf(15);
        Integer rateAccountCurrency = 10;
        Integer rateLinkedAccountCurrency = 20;
        Double expectedMirrorAmount = Double.valueOf(-75);
        Double expectedMirrorAmountCurrency = amountCurrency * -1;
        
        BankingAccount bankingAccount = this.factory.getBankingAccount(user);
        Currency currency = bankingAccount.getDefaultCurrency();
        currency.setRate(rateAccountCurrency);
        currency = this.currencyRepository.save(currency);
        bankingAccount.setDefaultCurrency(currency);
        bankingAccount = this.bankingAccountRepository.save(bankingAccount);

        BankingAccount linkedBankingAccount = this.factory.getBankingAccount(user);
        Currency linkedCurrency = linkedBankingAccount.getDefaultCurrency();
        linkedCurrency.setRate(rateLinkedAccountCurrency);
        linkedCurrency = this.currencyRepository.save(linkedCurrency);
        linkedBankingAccount.setDefaultCurrency(linkedCurrency);
        linkedBankingAccount = this.bankingAccountRepository.save(linkedBankingAccount);

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
    public void testCreateGroupTransaction() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createGroupBankingTransactionParameter);
    }


    @Test
    public void testCreateUserWithoutLinkedAccount() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setLinkedAccountId(null);

        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutThird() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setThirdId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutCategory() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setCategoryId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithoutClassification() throws BankingAccountNotExistException, BadAssociationBankingTransactionBankingAccountException, UserNotInGroupException, ThirdNotExistException, ThirdNoUserException, CategoryNotExistException, CategoryNoUserException, ClassificationNotExistException, ClassificationNoUserException, CurrencyNotExistException{
        this.createUserBankingTransactionParameter.setClassificationId(null);
        
        BankingTransactionDTO bankingTransactionDTO = this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName());
        BankingTransaction bankingTransaction = this.bankingTransactionRepository.findById(bankingTransactionDTO.getId()).orElseThrow();
        this.checkData(bankingTransactionDTO, bankingTransaction, this.createUserBankingTransactionParameter);
    }

    @Test
    public void testCreateUserWithBankingAccountNotExistException() {
        this.createUserBankingTransactionParameter.setAccountId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedBankingAccountNotExistException() {
        this.createUserBankingTransactionParameter.setLinkedAccountId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(BankingAccountNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithThirdNotExistException() {
        this.createUserBankingTransactionParameter.setThirdId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCategoryNotExistException() {
        this.createUserBankingTransactionParameter.setCategoryId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(CategoryNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithClassificationNotExistException() {
        this.createUserBankingTransactionParameter.setClassificationId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithCurrencyNotExistException() {
        this.createUserBankingTransactionParameter.setCurrencyId(this.factory.getRandomInteger());
        
        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.userRepository.save(this.factory.getUser());
        BankingAccount otherBankingAccount = this.factory.getBankingAccount(otherUser);
        Currency currency = this.currencyRepository.save(otherBankingAccount.getDefaultCurrency());
        otherBankingAccount.setDefaultCurrency(currency);
        otherBankingAccount = this.bankingAccountRepository.save(otherBankingAccount);

        this.createUserBankingTransactionParameter.setAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationBankingTransactionBankingAccountException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithLinkedAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.userRepository.save(this.factory.getUser());
        BankingAccount otherBankingAccount = this.factory.getBankingAccount(otherUser);
        Currency currency = this.currencyRepository.save(otherBankingAccount.getDefaultCurrency());
        otherBankingAccount.setDefaultCurrency(currency);
        otherBankingAccount = this.bankingAccountRepository.save(otherBankingAccount);

        this.createUserBankingTransactionParameter.setLinkedAccountId(otherBankingAccount.getId());
        
        Assertions.assertThrows(BadAssociationBankingTransactionBankingAccountException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createUserBankingTransactionParameter, this.user.getUserName()));
    }

    @Test
    public void testCreateUserWithGroupAccountBadAssociationBankingTransactionBankingAccountException() {
        User otherUser = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.bankingTransactionServiceImpl.createBankingTransaction(this.createGroupBankingTransactionParameter, otherUser.getUserName()));
    }
    
}
