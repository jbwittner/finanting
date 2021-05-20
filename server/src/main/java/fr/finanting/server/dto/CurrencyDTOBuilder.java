package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.CurrencyDTO;
import fr.finanting.server.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDTOBuilder extends Transformer<Currency, CurrencyDTO>{

    @Override
    public CurrencyDTO transform(final Currency input) {
        final CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setDefaultCurrency(input.getDefaultCurrency());
        currencyDTO.setId(input.getId());
        currencyDTO.setIsoCode(input.getIsoCode());
        currencyDTO.setLabel(input.getLabel());
        currencyDTO.setRate(input.getRate());
        currencyDTO.setDecimalPlaces(input.getDecimalPlaces());
        currencyDTO.setSymbol(input.getSymbol());
        return currencyDTO;
    }

    @Override
    public List<CurrencyDTO> transformAll(final List<Currency> input) {
        final List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        input.forEach(currency -> currencyDTOList.add(this.transform(currency)));
        return currencyDTOList;
    }
}
