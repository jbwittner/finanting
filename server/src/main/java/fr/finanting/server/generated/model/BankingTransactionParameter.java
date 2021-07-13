package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankingTransactionParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-13T16:22:45.636055+02:00[Europe/Paris]")
public class BankingTransactionParameter   {
  @JsonProperty("accountId")
  private Integer accountId;

  @JsonProperty("linkedAccountId")
  private Integer linkedAccountId;

  @JsonProperty("thirdId")
  private Integer thirdId;

  @JsonProperty("classificationId")
  private Integer classificationId;

  @JsonProperty("categoryId")
  private Integer categoryId;

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

  @JsonProperty("currencyId")
  private Integer currencyId;

  @JsonProperty("description")
  private String description;

  public BankingTransactionParameter accountId(Integer accountId) {
    this.accountId = accountId;
    return this;
  }

  /**
   * Get accountId
   * @return accountId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public BankingTransactionParameter linkedAccountId(Integer linkedAccountId) {
    this.linkedAccountId = linkedAccountId;
    return this;
  }

  /**
   * Get linkedAccountId
   * @return linkedAccountId
  */
  @ApiModelProperty(value = "")


  public Integer getLinkedAccountId() {
    return linkedAccountId;
  }

  public void setLinkedAccountId(Integer linkedAccountId) {
    this.linkedAccountId = linkedAccountId;
  }

  public BankingTransactionParameter thirdId(Integer thirdId) {
    this.thirdId = thirdId;
    return this;
  }

  /**
   * Get thirdId
   * @return thirdId
  */
  @ApiModelProperty(value = "")


  public Integer getThirdId() {
    return thirdId;
  }

  public void setThirdId(Integer thirdId) {
    this.thirdId = thirdId;
  }

  public BankingTransactionParameter classificationId(Integer classificationId) {
    this.classificationId = classificationId;
    return this;
  }

  /**
   * Get classificationId
   * @return classificationId
  */
  @ApiModelProperty(value = "")


  public Integer getClassificationId() {
    return classificationId;
  }

  public void setClassificationId(Integer classificationId) {
    this.classificationId = classificationId;
  }

  public BankingTransactionParameter categoryId(Integer categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * Get categoryId
   * @return categoryId
  */
  @ApiModelProperty(value = "")


  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public BankingTransactionParameter transactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public BankingTransactionParameter amountDate(Date amountDate) {
    this.amountDate = amountDate;
    return this;
  }

  /**
   * Get amountDate
   * @return amountDate
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Date getAmountDate() {
    return amountDate;
  }

  public void setAmountDate(Date amountDate) {
    this.amountDate = amountDate;
  }

  public BankingTransactionParameter amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public BankingTransactionParameter currencyAmount(Double currencyAmount) {
    this.currencyAmount = currencyAmount;
    return this;
  }

  /**
   * Get currencyAmount
   * @return currencyAmount
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getCurrencyAmount() {
    return currencyAmount;
  }

  public void setCurrencyAmount(Double currencyAmount) {
    this.currencyAmount = currencyAmount;
  }

  public BankingTransactionParameter currencyId(Integer currencyId) {
    this.currencyId = currencyId;
    return this;
  }

  /**
   * Get currencyId
   * @return currencyId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(Integer currencyId) {
    this.currencyId = currencyId;
  }

  public BankingTransactionParameter description(String description) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankingTransactionParameter bankingTransactionParameter = (BankingTransactionParameter) o;
    return Objects.equals(this.accountId, bankingTransactionParameter.accountId) &&
        Objects.equals(this.linkedAccountId, bankingTransactionParameter.linkedAccountId) &&
        Objects.equals(this.thirdId, bankingTransactionParameter.thirdId) &&
        Objects.equals(this.classificationId, bankingTransactionParameter.classificationId) &&
        Objects.equals(this.categoryId, bankingTransactionParameter.categoryId) &&
        Objects.equals(this.transactionDate, bankingTransactionParameter.transactionDate) &&
        Objects.equals(this.amountDate, bankingTransactionParameter.amountDate) &&
        Objects.equals(this.amount, bankingTransactionParameter.amount) &&
        Objects.equals(this.currencyAmount, bankingTransactionParameter.currencyAmount) &&
        Objects.equals(this.currencyId, bankingTransactionParameter.currencyId) &&
        Objects.equals(this.description, bankingTransactionParameter.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, linkedAccountId, thirdId, classificationId, categoryId, transactionDate, amountDate, amount, currencyAmount, currencyId, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankingTransactionParameter {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    linkedAccountId: ").append(toIndentedString(linkedAccountId)).append("\n");
    sb.append("    thirdId: ").append(toIndentedString(thirdId)).append("\n");
    sb.append("    classificationId: ").append(toIndentedString(classificationId)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
    sb.append("    amountDate: ").append(toIndentedString(amountDate)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currencyAmount: ").append(toIndentedString(currencyAmount)).append("\n");
    sb.append("    currencyId: ").append(toIndentedString(currencyId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

