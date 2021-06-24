package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.AddressDTO;
import fr.finanting.server.generated.model.BankDetailsDTO;
import fr.finanting.server.generated.model.CurrencyDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankingAccountDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-24T15:20:15.128749+02:00[Europe/Paris]")
public class BankingAccountDTO   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("balance")
  private Double balance;

  @JsonProperty("bankDetailsDTO")
  private BankDetailsDTO bankDetailsDTO;

  @JsonProperty("currencyDTO")
  private CurrencyDTO currencyDTO;

  @JsonProperty("addressDTO")
  private AddressDTO addressDTO;

  public BankingAccountDTO id(Integer id) {
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

  public BankingAccountDTO label(String label) {
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

  public BankingAccountDTO abbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
    return this;
  }

  /**
   * Get abbreviation
   * @return abbreviation
  */
  @ApiModelProperty(value = "")


  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public BankingAccountDTO balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  @ApiModelProperty(value = "")


  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public BankingAccountDTO bankDetailsDTO(BankDetailsDTO bankDetailsDTO) {
    this.bankDetailsDTO = bankDetailsDTO;
    return this;
  }

  /**
   * Get bankDetailsDTO
   * @return bankDetailsDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public BankDetailsDTO getBankDetailsDTO() {
    return bankDetailsDTO;
  }

  public void setBankDetailsDTO(BankDetailsDTO bankDetailsDTO) {
    this.bankDetailsDTO = bankDetailsDTO;
  }

  public BankingAccountDTO currencyDTO(CurrencyDTO currencyDTO) {
    this.currencyDTO = currencyDTO;
    return this;
  }

  /**
   * Get currencyDTO
   * @return currencyDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public CurrencyDTO getCurrencyDTO() {
    return currencyDTO;
  }

  public void setCurrencyDTO(CurrencyDTO currencyDTO) {
    this.currencyDTO = currencyDTO;
  }

  public BankingAccountDTO addressDTO(AddressDTO addressDTO) {
    this.addressDTO = addressDTO;
    return this;
  }

  /**
   * Get addressDTO
   * @return addressDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public AddressDTO getAddressDTO() {
    return addressDTO;
  }

  public void setAddressDTO(AddressDTO addressDTO) {
    this.addressDTO = addressDTO;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankingAccountDTO bankingAccountDTO = (BankingAccountDTO) o;
    return Objects.equals(this.id, bankingAccountDTO.id) &&
        Objects.equals(this.label, bankingAccountDTO.label) &&
        Objects.equals(this.abbreviation, bankingAccountDTO.abbreviation) &&
        Objects.equals(this.balance, bankingAccountDTO.balance) &&
        Objects.equals(this.bankDetailsDTO, bankingAccountDTO.bankDetailsDTO) &&
        Objects.equals(this.currencyDTO, bankingAccountDTO.currencyDTO) &&
        Objects.equals(this.addressDTO, bankingAccountDTO.addressDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, abbreviation, balance, bankDetailsDTO, currencyDTO, addressDTO);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankingAccountDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    bankDetailsDTO: ").append(toIndentedString(bankDetailsDTO)).append("\n");
    sb.append("    currencyDTO: ").append(toIndentedString(currencyDTO)).append("\n");
    sb.append("    addressDTO: ").append(toIndentedString(addressDTO)).append("\n");
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

