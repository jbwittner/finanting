package fr.finanting.server.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import fr.finanting.server.generated.model.CurrencyDTO;
import fr.finanting.server.generated.model.CurrencyParameter;
import fr.finanting.server.dto.CurrencyDTOBuilder;
import fr.finanting.server.exception.CurrencyUsedException;
import fr.finanting.server.repository.BankingAccountRepository;
import fr.finanting.server.repository.BankingTransactionRepository;
import fr.finanting.server.repository.ThirdRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.model.Currency;
import fr.finanting.server.repository.CurrencyRepository;
import fr.finanting.server.service.CurrencyService;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ThirdRepository thirdRepository;
    private final BankingAccountRepository bankingAccountRepository;
    private final BankingTransactionRepository bankingTransactionRepository;

    private static final CurrencyDTOBuilder CURRENCY_DTO_BUILDER = new CurrencyDTOBuilder();

    @Autowired
    public CurrencyServiceImpl(final CurrencyRepository currencyRepository,
                               final ThirdRepository thirdRepository,
                               final BankingAccountRepository bankingAccountRepository,
                               final BankingTransactionRepository bankingTransactionRepository){
        this.currencyRepository = currencyRepository;
        this.thirdRepository = thirdRepository;
        this.bankingAccountRepository = bankingAccountRepository;
        this.bankingTransactionRepository = bankingTransactionRepository;
    }

    @Override
    public void createCurrency(final CurrencyParameter currencyParameter) throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException{

        final String isoCode = currencyParameter.getIsoCode().toUpperCase();

        if(this.currencyRepository.existsByIsoCode(isoCode)){
            throw new CurrencyIsoCodeAlreadyExist(isoCode);
        }

        if(!this.currencyRepository.existsByDefaultCurrency(true)&&
                currencyParameter.getDefaultCurrency().equals(false)){
            throw new NoDefaultCurrencyException();
        }

        if(currencyParameter.getDefaultCurrency().equals(true)){
            final Optional<Currency> optionalDefaultApplicationCurrency = this.currencyRepository.findByDefaultCurrency(true);
            if(optionalDefaultApplicationCurrency.isPresent()){
                final Currency defaultApplicationCurrency = optionalDefaultApplicationCurrency.get();
                defaultApplicationCurrency.setDefaultCurrency(false);
                this.currencyRepository.save(defaultApplicationCurrency);
            }
        }

        final Currency currency = new Currency();

        currency.setDefaultCurrency(currencyParameter.getDefaultCurrency());
        final String label = StringUtils.capitalize(currencyParameter.getLabel().toLowerCase());
        currency.setLabel(label);
        currency.setSymbol(currencyParameter.getSymbol().toUpperCase());
        currency.setIsoCode(isoCode);
        currency.setRate(currencyParameter.getRate());
        currency.setDecimalPlaces(currencyParameter.getDecimalPlaces());

        this.currencyRepository.save(currency);
        
    }

    @Override
    public void updateCurrency(final Integer currencyId, final CurrencyParameter currencyParameter){
        final String isoCode = currencyParameter.getIsoCode().toUpperCase();

        final Optional<Currency> optionalCurrency = this.currencyRepository.findById(currencyId);

        if(optionalCurrency.isEmpty()){
            throw new CurrencyNotExistException(currencyId);
        }

        final Currency currentCurrency = optionalCurrency.get();

        final Optional<Currency> optionalCheckIsoCodeCurrency = this.currencyRepository.findByIsoCode(isoCode);

        if(optionalCheckIsoCodeCurrency.isPresent()){
            final Currency checkIsoCodeCurrency = optionalCheckIsoCodeCurrency.get();
            if(!checkIsoCodeCurrency.getId().equals(currentCurrency.getId())){
                throw new CurrencyIsoCodeAlreadyExist(isoCode);
            }
        }

        if(currencyParameter.getDefaultCurrency().equals(false) &&
            currentCurrency.getDefaultCurrency().equals(true)){
            throw new NoDefaultCurrencyException();
        }

        currentCurrency.setDefaultCurrency(currencyParameter.getDefaultCurrency());
        final String label = StringUtils.capitalize(currencyParameter.getLabel().toLowerCase());
        currentCurrency.setLabel(label);
        currentCurrency.setSymbol(currencyParameter.getSymbol().toUpperCase());
        currentCurrency.setIsoCode(isoCode);
        currentCurrency.setRate(currencyParameter.getRate());
        currentCurrency.setDecimalPlaces(currencyParameter.getDecimalPlaces());

        this.currencyRepository.save(currentCurrency);

    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        final List<Currency> currencies = this.currencyRepository.findAll();
        return CURRENCY_DTO_BUILDER.transformAll(currencies);
    }

    @Override
    public void deleteCurrency(final Integer currencyId) {
        final Currency currency = this.currencyRepository.findById(currencyId).
                orElseThrow(() -> new CurrencyNotExistException(currencyId));

        if(this.bankingAccountRepository.existsByDefaultCurrency(currency) ||
            this.thirdRepository.existsByDefaultCurrency(currency) ||
            this.bankingTransactionRepository.existsByCurrency(currency)){
            throw new CurrencyUsedException(currency.getIsoCode());
        }

        this.currencyRepository.delete(currency);

    }

}
