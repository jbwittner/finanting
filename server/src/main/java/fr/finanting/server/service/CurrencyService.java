package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.codegen.model.CurrencyDTO;
import fr.finanting.server.codegen.model.CurrencyParameter;
import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;

public interface CurrencyService {

    void createCurrency(final CurrencyParameter currencyParameter)
        throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException;
    
    void updateCurrency(Integer currencyId, CurrencyParameter currencyParameter)
        throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException;

    List<CurrencyDTO> getAllCurrencies();

    void deleteCurrency(Integer currencyId);

}
