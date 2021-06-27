package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.AddressParameter;
import fr.finanting.server.generated.model.BankDetailsParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateBankingAccountParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-27T23:54:16.397484+02:00[Europe/Paris]")
public class UpdateBankingAccountParameter   {
  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("defaultCurrencyISOCode")
  private String defaultCurrencyISOCode;

  @JsonProperty("initialBalance")
  private Double initialBalance;

  @JsonProperty("bankDetailsParameter")
  private BankDetailsParameter bankDetailsParameter;

  @JsonProperty("addressParameter")
  private AddressParameter addressParameter;

  public UpdateBankingAccountParameter label(String label) {
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

  public UpdateBankingAccountParameter abbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
    return this;
  }

  /**
   * Get abbreviation
   * @return abbreviation
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=3,max=6) 
  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public UpdateBankingAccountParameter defaultCurrencyISOCode(String defaultCurrencyISOCode) {
    this.defaultCurrencyISOCode = defaultCurrencyISOCode;
    return this;
  }

  /**
   * Get defaultCurrencyISOCode
   * @return defaultCurrencyISOCode
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(min=3,max=3) 
  public String getDefaultCurrencyISOCode() {
    return defaultCurrencyISOCode;
  }

  public void setDefaultCurrencyISOCode(String defaultCurrencyISOCode) {
    this.defaultCurrencyISOCode = defaultCurrencyISOCode;
  }

  public UpdateBankingAccountParameter initialBalance(Double initialBalance) {
    this.initialBalance = initialBalance;
    return this;
  }

  /**
   * Get initialBalance
   * @return initialBalance
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(Double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public UpdateBankingAccountParameter bankDetailsParameter(BankDetailsParameter bankDetailsParameter) {
    this.bankDetailsParameter = bankDetailsParameter;
    return this;
  }

  /**
   * Get bankDetailsParameter
   * @return bankDetailsParameter
  */
  @ApiModelProperty(value = "")

  @Valid

  public BankDetailsParameter getBankDetailsParameter() {
    return bankDetailsParameter;
  }

  public void setBankDetailsParameter(BankDetailsParameter bankDetailsParameter) {
    this.bankDetailsParameter = bankDetailsParameter;
  }

  public UpdateBankingAccountParameter addressParameter(AddressParameter addressParameter) {
    this.addressParameter = addressParameter;
    return this;
  }

  /**
   * Get addressParameter
   * @return addressParameter
  */
  @ApiModelProperty(value = "")

  @Valid

  public AddressParameter getAddressParameter() {
    return addressParameter;
  }

  public void setAddressParameter(AddressParameter addressParameter) {
    this.addressParameter = addressParameter;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateBankingAccountParameter updateBankingAccountParameter = (UpdateBankingAccountParameter) o;
    return Objects.equals(this.label, updateBankingAccountParameter.label) &&
        Objects.equals(this.abbreviation, updateBankingAccountParameter.abbreviation) &&
        Objects.equals(this.defaultCurrencyISOCode, updateBankingAccountParameter.defaultCurrencyISOCode) &&
        Objects.equals(this.initialBalance, updateBankingAccountParameter.initialBalance) &&
        Objects.equals(this.bankDetailsParameter, updateBankingAccountParameter.bankDetailsParameter) &&
        Objects.equals(this.addressParameter, updateBankingAccountParameter.addressParameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, abbreviation, defaultCurrencyISOCode, initialBalance, bankDetailsParameter, addressParameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateBankingAccountParameter {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    defaultCurrencyISOCode: ").append(toIndentedString(defaultCurrencyISOCode)).append("\n");
    sb.append("    initialBalance: ").append(toIndentedString(initialBalance)).append("\n");
    sb.append("    bankDetailsParameter: ").append(toIndentedString(bankDetailsParameter)).append("\n");
    sb.append("    addressParameter: ").append(toIndentedString(addressParameter)).append("\n");
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

