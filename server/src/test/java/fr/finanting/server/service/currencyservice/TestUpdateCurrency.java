package fr.finanting.server.service.currencyservice;

import java.util.List;

import fr.finanting.server.generated.model.CurrencyParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.ThirdRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.implementation.CurrencyServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestUpdateCurrency extends AbstractMotherIntegrationTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ThirdRepository thirdRepository;

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;

    private CurrencyServiceImpl currencyServiceImpl;

    private CurrencyParameter currencyParameter;

    @Override
    protected void initDataBeforeEach() {
        this.currencyServiceImpl = new CurrencyServiceImpl(this.currencyRepository,
                this.thirdRepository,
                this.bankingAccountRepository,
                this.bankingTransactionRepository);

        this.currencyParameter = new CurrencyParameter();
        this.currencyParameter.setDecimalPlaces(this.testFactory.getRandomInteger());
        this.currencyParameter.setDefaultCurrency(false);
        this.currencyParameter.setIsoCode(this.testFactory.getUniqueRandomAlphanumericString(3));
        this.currencyParameter.setLabel(this.testFactory.getUniqueRandomAlphanumericString().toLowerCase());
        this.currencyParameter.setRate(this.testFactory.getRandomInteger());
        this.currencyParameter.setSymbol(this.testFactory.getUniqueRandomAlphanumericString(3).toLowerCase());        
    }

    @Test
    public void testUpdateNoDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.testFactory.getCurrency();

        this.currencyServiceImpl.updateCurrency(currency.getId(), currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.testFactory.getCurrency();

        this.currencyParameter.setDefaultCurrency(true);

        this.currencyServiceImpl.updateCurrency(currency.getId(), currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateCurrencyWithoutUpdateIsoCode() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.testFactory.getCurrency();

        this.currencyParameter.setIsoCode(currency.getIsoCode());

        this.currencyServiceImpl.updateCurrency(currency.getId(), currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateCurrencyWithIsoCodeAlreadyUsed() {
        final Currency currency = this.testFactory.getCurrency();
        final Currency otherCurrency = this.testFactory.getCurrency();

        this.currencyParameter.setIsoCode(otherCurrency.getIsoCode());

        Assertions.assertThrows(CurrencyIsoCodeAlreadyExist.class,
            () -> this.currencyServiceImpl.updateCurrency(currency.getId(), currencyParameter));

    }

    @Test
    public void testUpdateCurrencyToNoDefaultCurrency() {
        final Currency currency = this.testFactory.getCurrency();
        currency.setDefaultCurrency(true);

        Assertions.assertThrows(NoDefaultCurrencyException.class,
            () -> this.currencyServiceImpl.updateCurrency(currency.getId(), currencyParameter));

    }

    @Test
    public void testUpdateCurrencyWithCurrencyNotExist() {

        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.currencyServiceImpl.updateCurrency(this.testFactory.getRandomInteger(), currencyParameter));

    }
    
}
