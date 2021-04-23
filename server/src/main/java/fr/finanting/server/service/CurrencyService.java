package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.dto.CurrencyDTO;
import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.parameter.CreateCurrencyParameter;
import fr.finanting.server.parameter.UpdateCurrencyParameter;

public interface CurrencyService {

    public void createCurrency(final CreateCurrencyParameter createCurrencyParameter)
        throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException;
    
    public void updateCurrency(final UpdateCurrencyParameter updateCurrencyParameter)
        throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException;

    public List<CurrencyDTO> getAllCurrencies();

}
