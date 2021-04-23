package fr.finanting.server.dto;

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
    
}
