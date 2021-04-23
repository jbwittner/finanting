package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.CurrencyDTO;
import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.parameter.CreateCurrencyParameter;
import fr.finanting.server.parameter.UpdateCurrencyParameter;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(final CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void createCurrency(final CreateCurrencyParameter createCurrencyParameter) throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{

        final String isoCode = createCurrencyParameter.getIsoCode().toUpperCase();

        if(this.currencyRepository.existsByIsoCode(isoCode)){
            throw new CurrencyIsoCodeAlreadyExist(isoCode);
        }

        if(!this.currencyRepository.existsByDefaultCurrency(true)&&
            createCurrencyParameter.getDefaultCurrency().equals(false)){
            throw new NoDefaultCurrencyException();
        }

        if(createCurrencyParameter.getDefaultCurrency().equals(true)){
            final Optional<Currency> optionalDefaultApplicationCurrency = this.currencyRepository.findByDefaultCurrency(true);
            if(optionalDefaultApplicationCurrency.isPresent()){
                final Currency defaultApplicationCurrency = optionalDefaultApplicationCurrency.get();
                defaultApplicationCurrency.setDefaultCurrency(false);
                this.currencyRepository.save(defaultApplicationCurrency);
            }
        }

        final Currency currency = new Currency();

        currency.setDefaultCurrency(createCurrencyParameter.getDefaultCurrency());
        final String label = StringUtils.capitalize(createCurrencyParameter.getLabel().toLowerCase());
        currency.setLabel(label);
        currency.setSymbol(createCurrencyParameter.getSymbol().toUpperCase());
        currency.setIsoCode(isoCode);
        currency.setRate(createCurrencyParameter.getRate());
        currency.setDecimalPlaces(createCurrencyParameter.getDecimalPlaces());

        this.currencyRepository.save(currency);
        
    }

    @Override
    public void updateCurrency(final UpdateCurrencyParameter updateCurrencyParameter) throws CurrencyIsoCodeAlreadyExist, CurrencyNotExistException, NoDefaultCurrencyException{
        final String isoCode = updateCurrencyParameter.getIsoCode().toUpperCase();

        final Optional<Currency> optionalCurrency = this.currencyRepository.findById(updateCurrencyParameter.getId());

        if(!optionalCurrency.isPresent()){
            throw new CurrencyNotExistException(updateCurrencyParameter.getId());
        }

        final Currency currentCurrency = optionalCurrency.get();

        final Optional<Currency> optionalCheckIsoCodeCurrency = this.currencyRepository.findByIsoCode(isoCode);

        if(optionalCheckIsoCodeCurrency.isPresent()){
            final Currency checkIsoCodeCurrency = optionalCheckIsoCodeCurrency.get();
            if(!checkIsoCodeCurrency.getId().equals(currentCurrency.getId())){
                throw new CurrencyIsoCodeAlreadyExist(isoCode);
            }
        }

        if(updateCurrencyParameter.getDefaultCurrency().equals(false) &&
            currentCurrency.getDefaultCurrency().equals(true)){
            throw new NoDefaultCurrencyException();
        }

        currentCurrency.setDefaultCurrency(updateCurrencyParameter.getDefaultCurrency());
        final String label = StringUtils.capitalize(updateCurrencyParameter.getLabel().toLowerCase());
        currentCurrency.setLabel(label);
        currentCurrency.setSymbol(updateCurrencyParameter.getSymbol().toUpperCase());
        currentCurrency.setIsoCode(isoCode);
        currentCurrency.setRate(updateCurrencyParameter.getRate());
        currentCurrency.setDecimalPlaces(updateCurrencyParameter.getDecimalPlaces());

        this.currencyRepository.save(currentCurrency);

    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        final List<CurrencyDTO> currencyDTOs = new ArrayList<>();

        final List<Currency> currencies = this.currencyRepository.findAll();

        CurrencyDTO currencyDTO;

        for(final Currency currency : currencies){
            currencyDTO = new CurrencyDTO(currency);
            currencyDTOs.add(currencyDTO);
        }

        return currencyDTOs;
    }
    
}
