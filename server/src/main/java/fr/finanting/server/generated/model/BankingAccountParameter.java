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
 * BankingAccountParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-08T10:10:39.885064+02:00[Europe/Paris]")
public class BankingAccountParameter   {
  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("defaultCurrencyISOCode")
  private String defaultCurrencyISOCode;

  @JsonProperty("initialBalance")
  private Double initialBalance;

  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("bankDetailsParameter")
  private BankDetailsParameter bankDetailsParameter;

  @JsonProperty("addressParameter")
  private AddressParameter addressParameter;

  public BankingAccountParameter label(String label) {
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

  public BankingAccountParameter abbreviation(String abbreviation) {
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

  public BankingAccountParameter defaultCurrencyISOCode(String defaultCurrencyISOCode) {
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

  public BankingAccountParameter initialBalance(Double initialBalance) {
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

  public BankingAccountParameter groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * Get groupName
   * @return groupName
  */
  @ApiModelProperty(value = "")


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public BankingAccountParameter bankDetailsParameter(BankDetailsParameter bankDetailsParameter) {
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

  public BankingAccountParameter addressParameter(AddressParameter addressParameter) {
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
    BankingAccountParameter bankingAccountParameter = (BankingAccountParameter) o;
    return Objects.equals(this.label, bankingAccountParameter.label) &&
        Objects.equals(this.abbreviation, bankingAccountParameter.abbreviation) &&
        Objects.equals(this.defaultCurrencyISOCode, bankingAccountParameter.defaultCurrencyISOCode) &&
        Objects.equals(this.initialBalance, bankingAccountParameter.initialBalance) &&
        Objects.equals(this.groupName, bankingAccountParameter.groupName) &&
        Objects.equals(this.bankDetailsParameter, bankingAccountParameter.bankDetailsParameter) &&
        Objects.equals(this.addressParameter, bankingAccountParameter.addressParameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, abbreviation, defaultCurrencyISOCode, initialBalance, groupName, bankDetailsParameter, addressParameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankingAccountParameter {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    defaultCurrencyISOCode: ").append(toIndentedString(defaultCurrencyISOCode)).append("\n");
    sb.append("    initialBalance: ").append(toIndentedString(initialBalance)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
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

