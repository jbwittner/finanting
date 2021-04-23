package fr.finanting.server.dto;

import fr.finanting.server.model.Currency;
import lombok.Data;

@Data
public class CurrencyDTO {

    private Integer id;
    private Boolean defaultCurrency;
    private String label;
    private String symbol;
    private String isoCode;
    private Integer rate;
    private Integer decimalPlaces;

    public CurrencyDTO(Currency currency){
        this.id = currency.getId();
        this.defaultCurrency = currency.getDefaultCurrency();
        this.label = currency.getLabel();
        this.symbol = currency.getSymbol();
        this.isoCode = currency.getIsoCode();
        this.rate = currency.getRate();
        this.decimalPlaces = currency.getDecimalPlaces();
    }
    
}
