package fr.finanting.server.service.currencyservice;

import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.CurrencyUsedException;
import fr.finanting.server.model.BankingAccount;
import fr.finanting.server.model.BankingTransaction;
import fr.finanting.server.model.Currency;
import fr.finanting.server.model.Third;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.service.implementation.CurrencyServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDeleteCurrency extends AbstractMotherIntegrationTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ThirdRepository thirdRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;

    private CurrencyServiceImpl currencyServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.currencyServiceImpl = new CurrencyServiceImpl(this.currencyRepository,
                this.thirdRepository,
                this.bankingAccountRepository,
                this.bankingTransactionRepository);
    }

    @Test
    public void testDeleteUnusedCurrency(){
        final Currency currency = this.testFactory.getCurrency();
        final int currencyId = currency.getId();
        this.currencyServiceImpl.deleteCurrency(currencyId);
        final boolean result = this.currencyRepository.existsById(currencyId);
        Assertions.assertFalse(result);
    }
    @Test
    public void testDeleteNonExistCurrency(){
        final int currencyId = this.testFactory.getRandomInteger();
        Assertions.assertThrows(CurrencyNotExistException.class, () -> this.currencyServiceImpl.deleteCurrency(currencyId));
    }

    @Test
    public void testDeleteCurrencyUsedByThird(){
        final Third third = this.testFactory.getThird(this.testFactory.getUser());
        final Currency currency = third.getDefaultCurrency();
        final int currencyId = currency.getId();
        Assertions.assertThrows(CurrencyUsedException.class, () -> this.currencyServiceImpl.deleteCurrency(currencyId));
    }

    @Test
    public void testDeleteCurrencyUsedByBankingAccount(){
        final BankingAccount bankingAccount = this.testFactory.getBankingAccount(this.testFactory.getUser());
        final Currency currency = bankingAccount.getDefaultCurrency();
        final int currencyId = currency.getId();
        Assertions.assertThrows(CurrencyUsedException.class, () -> this.currencyServiceImpl.deleteCurrency(currencyId));
    }

    @Test
    public void testDeleteCurrencyUsedByBankingTransaction(){
        final BankingTransaction bankingTransaction = this.testFactory.getBankingTransaction(this.testFactory.getUser(), false);
        final Currency currency = bankingTransaction.getCurrency();
        final int currencyId = currency.getId();
        Assertions.assertThrows(CurrencyUsedException.class, () -> this.currencyServiceImpl.deleteCurrency(currencyId));
    }

}
