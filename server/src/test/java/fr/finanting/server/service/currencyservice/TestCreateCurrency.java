package fr.finanting.server.service.currencyservice;

import java.util.List;

import fr.finanting.server.codegen.model.CurrencyParameter;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.ThirdRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.implementation.CurrencyServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateCurrency extends AbstractMotherIntegrationTest{

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
        this.currencyParameter.setIsoCode(this.testFactory.getRandomAlphanumericString(3));
        this.currencyParameter.setLabel(this.testFactory.getRandomAlphanumericString().toLowerCase());
        this.currencyParameter.setRate(this.testFactory.getRandomInteger());
        this.currencyParameter.setSymbol(this.testFactory.getRandomAlphanumericString(3).toLowerCase());
    }

    @Test
    public void testCreateFirstDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        this.currencyParameter.setDefaultCurrency(true);
        this.currencyServiceImpl.createCurrency(this.currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        final Currency currency = currencies.get(0);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.isDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());
    }

    @Test
    public void testCreateAnotherDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        Currency otherCurrency = this.testFactory.getCurrency();
        otherCurrency.setDefaultCurrency(true);

        this.currencyParameter.setDefaultCurrency(true);
        this.currencyServiceImpl.createCurrency(this.currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(2, currencies.size());

        final Currency currency = currencies.get(1);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

        otherCurrency = currencies.get(0);

        Assertions.assertEquals(true, currency.getDefaultCurrency());
        Assertions.assertEquals(false, otherCurrency.getDefaultCurrency());

    }

    @Test
    public void testCreateAnotherNoDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        final Currency otherCurrency = this.testFactory.getCurrency(true);

        this.currencyServiceImpl.createCurrency(this.currencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(2, currencies.size());

        final Currency currency = currencies.get(1);

        Assertions.assertEquals(this.currencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.currencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.currencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.currencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.currencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

        Assertions.assertEquals(false, currency.getDefaultCurrency());
        Assertions.assertEquals(true, otherCurrency.getDefaultCurrency());

    }

    @Test
    public void testCreateFirstNoDefaultCurrencyOk() {
        
        Assertions.assertThrows(NoDefaultCurrencyException.class,
            () -> this.currencyServiceImpl.createCurrency(this.currencyParameter));
        
    }

    @Test
    public void testCreateCurrencyWithAlreadyExitedIsoCode(){
        final Currency currency = this.testFactory.getCurrency();
        currency.setDefaultCurrency(true);

        this.currencyParameter.setIsoCode(currency.getIsoCode());

        Assertions.assertThrows(CurrencyIsoCodeAlreadyExist.class,
            () -> this.currencyServiceImpl.createCurrency(this.currencyParameter));
    }
}
