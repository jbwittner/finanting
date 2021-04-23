package fr.finanting.server.service.currencyservice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.parameter.UpdateCurrencyParameter;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.implementation.CurrencyServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestUpdateCurrency extends AbstractMotherIntegrationTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    private CurrencyServiceImpl currencyServiceImpl;

    private UpdateCurrencyParameter updateCurrencyParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.currencyServiceImpl = new CurrencyServiceImpl(this.currencyRepository);

        this.updateCurrencyParameter = new UpdateCurrencyParameter();
        this.updateCurrencyParameter.setDecimalPlaces(this.factory.getRandomInteger());
        this.updateCurrencyParameter.setDefaultCurrency(false);
        this.updateCurrencyParameter.setIsoCode(this.factory.getUniqueRandomAlphanumericString(3));
        this.updateCurrencyParameter.setLabel(this.factory.getUniqueRandomAlphanumericString().toLowerCase());
        this.updateCurrencyParameter.setRate(this.factory.getRandomInteger());
        this.updateCurrencyParameter.setSymbol(this.factory.getUniqueRandomAlphanumericString(3).toLowerCase());        
    }

    @Test
    public void testUpdateNoDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.currencyRepository.save(this.factory.getCurrency());

        this.updateCurrencyParameter.setId(currency.getId());

        this.currencyServiceImpl.updateCurrency(updateCurrencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.updateCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.updateCurrencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.updateCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.updateCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.updateCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.updateCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateDefaultCurrencyOk() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.currencyRepository.save(this.factory.getCurrency());

        this.updateCurrencyParameter.setId(currency.getId());
        this.updateCurrencyParameter.setDefaultCurrency(true);

        this.currencyServiceImpl.updateCurrency(updateCurrencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.updateCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.updateCurrencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.updateCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.updateCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.updateCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.updateCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateCurrencyWithoutUpdateIsoCode() throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        Currency currency = this.currencyRepository.save(this.factory.getCurrency());

        this.updateCurrencyParameter.setId(currency.getId());
        this.updateCurrencyParameter.setIsoCode(currency.getIsoCode());

        this.currencyServiceImpl.updateCurrency(updateCurrencyParameter);

        final List<Currency> currencies = this.currencyRepository.findAll();

        Assertions.assertEquals(1, currencies.size());

        currency = currencies.get(0);

        Assertions.assertEquals(this.updateCurrencyParameter.getDecimalPlaces(), currency.getDecimalPlaces());
        Assertions.assertEquals(this.updateCurrencyParameter.getDefaultCurrency(), currency.getDefaultCurrency());
        Assertions.assertEquals(this.updateCurrencyParameter.getIsoCode().toUpperCase(), currency.getIsoCode());
        final String label = StringUtils.capitalize(this.updateCurrencyParameter.getLabel().toLowerCase());
        Assertions.assertEquals(label, currency.getLabel());
        Assertions.assertEquals(this.updateCurrencyParameter.getRate(), currency.getRate());
        Assertions.assertEquals(this.updateCurrencyParameter.getSymbol().toUpperCase(), currency.getSymbol());

    }

    @Test
    public void testUpdateCurrencyWithIsoCodeAlreadyUsed() {
        final Currency currency = this.currencyRepository.save(this.factory.getCurrency());
        final Currency otherCurrency = this.currencyRepository.save(this.factory.getCurrency());

        this.updateCurrencyParameter.setId(currency.getId());
        this.updateCurrencyParameter.setIsoCode(otherCurrency.getIsoCode());

        Assertions.assertThrows(CurrencyIsoCodeAlreadyExist.class,
            () -> this.currencyServiceImpl.updateCurrency(updateCurrencyParameter));

    }

    @Test
    public void testUpdateCurrencyToNoDefaultCurrency() {
        final Currency currency = this.factory.getCurrency();
        currency.setDefaultCurrency(true);
        this.currencyRepository.save(currency);

        this.updateCurrencyParameter.setId(currency.getId());

        Assertions.assertThrows(NoDefaultCurrencyException.class,
            () -> this.currencyServiceImpl.updateCurrency(updateCurrencyParameter));

    }

    @Test
    public void testUpdateCurrencyWithCurrencyNotExist() {
        this.updateCurrencyParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(CurrencyNotExistException.class,
            () -> this.currencyServiceImpl.updateCurrency(updateCurrencyParameter));

    }
    
}
