package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.BankingAccountDTO;
import fr.finanting.server.generated.model.CategoryDTO;
import fr.finanting.server.generated.model.ClassificationDTO;
import fr.finanting.server.generated.model.CurrencyDTO;
import fr.finanting.server.generated.model.ThirdDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankingTransactionDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-25T17:50:48.110612+02:00[Europe/Paris]")
public class BankingTransactionDTO   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("bankingAccountDTO")
  private BankingAccountDTO bankingAccountDTO;

  @JsonProperty("linkedBankingAccountDTO")
  private BankingAccountDTO linkedBankingAccountDTO;

  @JsonProperty("thirdDTO")
  private ThirdDTO thirdDTO;

  @JsonProperty("categoryDTO")
  private CategoryDTO categoryDTO;

  @JsonProperty("classificationDTO")
  private ClassificationDTO classificationDTO;

  @JsonProperty("transactionDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private Date transactionDate;

  @JsonProperty("amountDate")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private Date amountDate;

  @JsonProperty("amount")
  private Double amount;

  @JsonProperty("currencyAmount")
  private Double currencyAmount;

  @JsonProperty("currencyDTO")
  private CurrencyDTO currencyDTO;

  @JsonProperty("description")
  private String description;

  @JsonProperty("createTimestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private Date createTimestamp;

  @JsonProperty("updateTimestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private Date updateTimestamp;

  public BankingTransactionDTO id(Integer id) {
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

  public BankingTransactionDTO bankingAccountDTO(BankingAccountDTO bankingAccountDTO) {
    this.bankingAccountDTO = bankingAccountDTO;
    return this;
  }

  /**
   * Get bankingAccountDTO
   * @return bankingAccountDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public BankingAccountDTO getBankingAccountDTO() {
    return bankingAccountDTO;
  }

  public void setBankingAccountDTO(BankingAccountDTO bankingAccountDTO) {
    this.bankingAccountDTO = bankingAccountDTO;
  }

  public BankingTransactionDTO linkedBankingAccountDTO(BankingAccountDTO linkedBankingAccountDTO) {
    this.linkedBankingAccountDTO = linkedBankingAccountDTO;
    return this;
  }

  /**
   * Get linkedBankingAccountDTO
   * @return linkedBankingAccountDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public BankingAccountDTO getLinkedBankingAccountDTO() {
    return linkedBankingAccountDTO;
  }

  public void setLinkedBankingAccountDTO(BankingAccountDTO linkedBankingAccountDTO) {
    this.linkedBankingAccountDTO = linkedBankingAccountDTO;
  }

  public BankingTransactionDTO thirdDTO(ThirdDTO thirdDTO) {
    this.thirdDTO = thirdDTO;
    return this;
  }

  /**
   * Get thirdDTO
   * @return thirdDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public ThirdDTO getThirdDTO() {
    return thirdDTO;
  }

  public void setThirdDTO(ThirdDTO thirdDTO) {
    this.thirdDTO = thirdDTO;
  }

  public BankingTransactionDTO categoryDTO(CategoryDTO categoryDTO) {
    this.categoryDTO = categoryDTO;
    return this;
  }

  /**
   * Get categoryDTO
   * @return categoryDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public CategoryDTO getCategoryDTO() {
    return categoryDTO;
  }

  public void setCategoryDTO(CategoryDTO categoryDTO) {
    this.categoryDTO = categoryDTO;
  }

  public BankingTransactionDTO classificationDTO(ClassificationDTO classificationDTO) {
    this.classificationDTO = classificationDTO;
    return this;
  }

  /**
   * Get classificationDTO
   * @return classificationDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public ClassificationDTO getClassificationDTO() {
    return classificationDTO;
  }

  public void setClassificationDTO(ClassificationDTO classificationDTO) {
    this.classificationDTO = classificationDTO;
  }

  public BankingTransactionDTO transactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
  */
  @ApiModelProperty(value = "")

  @Valid

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public BankingTransactionDTO amountDate(Date amountDate) {
    this.amountDate = amountDate;
    return this;
  }

  /**
   * Get amountDate
   * @return amountDate
  */
  @ApiModelProperty(value = "")

  @Valid

  public Date getAmountDate() {
    return amountDate;
  }

  public void setAmountDate(Date amountDate) {
    this.amountDate = amountDate;
  }

  public BankingTransactionDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @ApiModelProperty(value = "")


  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public BankingTransactionDTO currencyAmount(Double currencyAmount) {
    this.currencyAmount = currencyAmount;
    return this;
  }

  /**
   * Get currencyAmount
   * @return currencyAmount
  */
  @ApiModelProperty(value = "")


  public Double getCurrencyAmount() {
    return currencyAmount;
  }

  public void setCurrencyAmount(Double currencyAmount) {
    this.currencyAmount = currencyAmount;
  }

  public BankingTransactionDTO currencyDTO(CurrencyDTO currencyDTO) {
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

  public BankingTransactionDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BankingTransactionDTO createTimestamp(Date createTimestamp) {
    this.createTimestamp = createTimestamp;
    return this;
  }

  /**
   * Get createTimestamp
   * @return createTimestamp
  */
  @ApiModelProperty(value = "")

  @Valid

  public Date getCreateTimestamp() {
    return createTimestamp;
  }

  public void setCreateTimestamp(Date createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public BankingTransactionDTO updateTimestamp(Date updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
    return this;
  }

  /**
   * Get updateTimestamp
   * @return updateTimestamp
  */
  @ApiModelProperty(value = "")

  @Valid

  public Date getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Date updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankingTransactionDTO bankingTransactionDTO = (BankingTransactionDTO) o;
    return Objects.equals(this.id, bankingTransactionDTO.id) &&
        Objects.equals(this.bankingAccountDTO, bankingTransactionDTO.bankingAccountDTO) &&
        Objects.equals(this.linkedBankingAccountDTO, bankingTransactionDTO.linkedBankingAccountDTO) &&
        Objects.equals(this.thirdDTO, bankingTransactionDTO.thirdDTO) &&
        Objects.equals(this.categoryDTO, bankingTransactionDTO.categoryDTO) &&
        Objects.equals(this.classificationDTO, bankingTransactionDTO.classificationDTO) &&
        Objects.equals(this.transactionDate, bankingTransactionDTO.transactionDate) &&
        Objects.equals(this.amountDate, bankingTransactionDTO.amountDate) &&
        Objects.equals(this.amount, bankingTransactionDTO.amount) &&
        Objects.equals(this.currencyAmount, bankingTransactionDTO.currencyAmount) &&
        Objects.equals(this.currencyDTO, bankingTransactionDTO.currencyDTO) &&
        Objects.equals(this.description, bankingTransactionDTO.description) &&
        Objects.equals(this.createTimestamp, bankingTransactionDTO.createTimestamp) &&
        Objects.equals(this.updateTimestamp, bankingTransactionDTO.updateTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bankingAccountDTO, linkedBankingAccountDTO, thirdDTO, categoryDTO, classificationDTO, transactionDate, amountDate, amount, currencyAmount, currencyDTO, description, createTimestamp, updateTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankingTransactionDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bankingAccountDTO: ").append(toIndentedString(bankingAccountDTO)).append("\n");
    sb.append("    linkedBankingAccountDTO: ").append(toIndentedString(linkedBankingAccountDTO)).append("\n");
    sb.append("    thirdDTO: ").append(toIndentedString(thirdDTO)).append("\n");
    sb.append("    categoryDTO: ").append(toIndentedString(categoryDTO)).append("\n");
    sb.append("    classificationDTO: ").append(toIndentedString(classificationDTO)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
    sb.append("    amountDate: ").append(toIndentedString(amountDate)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currencyAmount: ").append(toIndentedString(currencyAmount)).append("\n");
    sb.append("    currencyDTO: ").append(toIndentedString(currencyDTO)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createTimestamp: ").append(toIndentedString(createTimestamp)).append("\n");
    sb.append("    updateTimestamp: ").append(toIndentedString(updateTimestamp)).append("\n");
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

