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
 * CurrencyDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-08T10:10:39.885064+02:00[Europe/Paris]")
public class CurrencyDTO   {
  @JsonProperty("id")
  private Integer id;

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

  public CurrencyDTO id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CurrencyDTO defaultCurrency(Boolean defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
    return this;
  }

  /**
   * Get defaultCurrency
   * @return defaultCurrency
  */
  @ApiModelProperty(value = "")


  public Boolean getDefaultCurrency() {
    return defaultCurrency;
  }

  public void setDefaultCurrency(Boolean defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
  }

  public CurrencyDTO label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
  */
  @ApiModelProperty(value = "")


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CurrencyDTO symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * Get symbol
   * @return symbol
  */
  @ApiModelProperty(value = "")


  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public CurrencyDTO isoCode(String isoCode) {
    this.isoCode = isoCode;
    return this;
  }

  /**
   * Get isoCode
   * @return isoCode
  */
  @ApiModelProperty(value = "")


  public String getIsoCode() {
    return isoCode;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
  }

  public CurrencyDTO rate(Integer rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Get rate
   * @return rate
  */
  @ApiModelProperty(value = "")


  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
  }

  public CurrencyDTO decimalPlaces(Integer decimalPlaces) {
    this.decimalPlaces = decimalPlaces;
    return this;
  }

  /**
   * Get decimalPlaces
   * @return decimalPlaces
  */
  @ApiModelProperty(value = "")


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
    CurrencyDTO currencyDTO = (CurrencyDTO) o;
    return Objects.equals(this.id, currencyDTO.id) &&
        Objects.equals(this.defaultCurrency, currencyDTO.defaultCurrency) &&
        Objects.equals(this.label, currencyDTO.label) &&
        Objects.equals(this.symbol, currencyDTO.symbol) &&
        Objects.equals(this.isoCode, currencyDTO.isoCode) &&
        Objects.equals(this.rate, currencyDTO.rate) &&
        Objects.equals(this.decimalPlaces, currencyDTO.decimalPlaces);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, defaultCurrency, label, symbol, isoCode, rate, decimalPlaces);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrencyDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

