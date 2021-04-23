package fr.finanting.server.service.currencyservice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.parameter.CreateCurrencyParameter;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.implementation.CurrencyServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateCurrency extends AbstractMotherIntegrationTest{

    @Autowired
    private CurrencyRepository currencyRepository;

    private CurrencyServiceImpl currencyServiceImpl;

    private CreateCurrencyParameter createCurrencyParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.currencyServiceImpl = new CurrencyServiceImpl(this.currencyRepository);
        this.createCurrencyParameter = new CreateCurrencyParameter();
        this.createCurrencyParameter.setDecimalPlaces(this.factory.getRandomInteger());
        this.createCurrencyParameter.setDefaultCurrency(false);
        this.createCurrencyParameter.setIsoCode(this.factory.getRandomAlphanumericString(3));
        this.createCurrencyParameter.setLabel(this.factory.getRandomAlphanumericString().toLowerCase());
        this.createCurrencyParameter.setRate(this.factory.getRandomInteger());
        this.createCurrencyParameter.setSymbol(this.factory.getRandomAlphanumericString(3).toLowerCase());
    }

    @Test
    public void testCreateFirstDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        this.createCurrencyParameter.setDefaultCurrency(true);
        this.currencyServiceImpl.createCurrency(this.createCurrencyParameter);

        List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        Currency currency = currencies.get(0);

        Assertions.assertEquals(this.createCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.createCurrencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.createCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        String label = StringUtils.capitalize(this.createCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.createCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.createCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());
    }

    @Test
    public void testCreateAnotherDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        Currency otherCurrency = this.factory.getCurrency();
        otherCurrency.setDefaultCurrency(true);
        otherCurrency = this.currencyRepository.save(otherCurrency);

        this.createCurrencyParameter.setDefaultCurrency(true);
        this.currencyServiceImpl.createCurrency(this.createCurrencyParameter);

        List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(2, currencies.size());

        Currency currency = currencies.get(1);

        Assertions.assertEquals(this.createCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.createCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        String label = StringUtils.capitalize(this.createCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.createCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.createCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

        otherCurrency = currencies.get(0);

        Assertions.assertEquals(true, currency.getDefaultCurrency());
        Assertions.assertEquals(false, otherCurrency.getDefaultCurrency());

    }

    @Test
    public void testCreateAnotherNoDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{
        Currency otherCurrency = this.factory.getCurrency();
        otherCurrency.setDefaultCurrency(true);
        this.currencyRepository.save(otherCurrency);

        this.currencyServiceImpl.createCurrency(this.createCurrencyParameter);

        List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(2, currencies.size());

        Currency currency = currencies.get(1);

        Assertions.assertEquals(this.createCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.createCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        String label = StringUtils.capitalize(this.createCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.createCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.createCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

        otherCurrency = currencies.get(0);

        Assertions.assertEquals(false, currency.getDefaultCurrency());

    }

    @Test
    public void testCreateFirstNoDefaultCurrencyOk() {
        
        Assertions.assertThrows(NoDefaultCurrencyException.class,
            () -> this.currencyServiceImpl.createCurrency(this.createCurrencyParameter));
        
    }

    @Test
    public void testCreateCurrencyWithAlreadyExitedIsoCode(){
        Currency currency = this.currencyRepository.save(this.factory.getCurrency());

        this.createCurrencyParameter.setIsoCode(currency.getIsoCode());

        Assertions.assertThrows(CurrencyIsoCodeAlreadyExist.class,
            () -> this.currencyServiceImpl.createCurrency(this.createCurrencyParameter));
    }
}
