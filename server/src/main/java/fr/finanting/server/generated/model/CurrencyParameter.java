package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CurrencyParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-08T10:10:39.885064+02:00[Europe/Paris]")
public class CurrencyParameter   {
  @JsonProperty("defaultCurrency")
  private Boolean defaultCurrency;

  @JsonProperty("label")
  private String label;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("isoCode")
  private String isoCode;

  @JsonProperty("rate")
  private Integer rate;

  @JsonProperty("decimalPlaces")
  private Integer decimalPlaces;

  public CurrencyParameter defaultCurrency(Boolean defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
    return this;
  }

  /**
   * Get defaultCurrency
   * @return defaultCurrency
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean getDefaultCurrency() {
    return defaultCurrency;
  }

  public void setDefaultCurrency(Boolean defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
  }

  public CurrencyParameter label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CurrencyParameter symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * Get symbol
   * @return symbol
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(max=3) 
  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public CurrencyParameter isoCode(String isoCode) {
    this.isoCode = isoCode;
    return this;
  }

  /**
   * Get isoCode
   * @return isoCode
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=3,max=3) 
  public String getIsoCode() {
    return isoCode;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  public CurrencyParameter rate(Integer rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Get rate
   * @return rate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public CurrencyParameter decimalPlaces(Integer decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
    return this;
  }

  /**
   * Get decimalPlaces
   * @return decimalPlaces
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getDecimalPlaces() {
    return decimalPlaces;
  }

  public void setDecimalPlaces(Integer decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrencyParameter currencyParameter = (CurrencyParameter) o;
    return Objects.equals(this.defaultCurrency, currencyParameter.defaultCurrency) &&
        Objects.equals(this.label, currencyParameter.label) &&
        Objects.equals(this.symbol, currencyParameter.symbol) &&
        Objects.equals(this.isoCode, currencyParameter.isoCode) &&
        Objects.equals(this.rate, currencyParameter.rate) &&
        Objects.equals(this.decimalPlaces, currencyParameter.decimalPlaces);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultCurrency, label, symbol, isoCode, rate, decimalPlaces);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrencyParameter {\n");
    
    sb.append("    defaultCurrency: ").append(toIndentedString(defaultCurrency)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    symbol: ").append(toIndentedString(symbol)).append("\n");
    sb.append("    isoCode: ").append(toIndentedString(isoCode)).append("\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    decimalPlaces: ").append(toIndentedString(decimalPlaces)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

