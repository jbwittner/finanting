package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.AddressParameter;
import fr.finanting.server.generated.model.BankDetailsParameter;
import fr.finanting.server.generated.model.ContactParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ThirdParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-24T15:20:15.128749+02:00[Europe/Paris]")
public class ThirdParameter   {
  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("description")
  private String description;

  @JsonProperty("defaultCategoryId")
  private Integer defaultCategoryId;

  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("contactParameter")
  private ContactParameter contactParameter;

  @JsonProperty("bankDetailsParameter")
  private BankDetailsParameter bankDetailsParameter;

  @JsonProperty("addressParameter")
  private AddressParameter addressParameter;

  public ThirdParameter label(String label) {
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

  public ThirdParameter abbreviation(String abbreviation) {
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

  public ThirdParameter description(String description) {
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

  public ThirdParameter defaultCategoryId(Integer defaultCategoryId) {
    this.defaultCategoryId = defaultCategoryId;
    return this;
  }

  /**
   * Get defaultCategoryId
   * @return defaultCategoryId
  */
  @ApiModelProperty(value = "")


  public Integer getDefaultCategoryId() {
    return defaultCategoryId;
  }

  public void setDefaultCategoryId(Integer defaultCategoryId) {
    this.defaultCategoryId = defaultCategoryId;
  }

  public ThirdParameter groupName(String groupName) {
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

  public ThirdParameter contactParameter(ContactParameter contactParameter) {
    this.contactParameter = contactParameter;
    return this;
  }

  /**
   * Get contactParameter
   * @return contactParameter
  */
  @ApiModelProperty(value = "")

  @Valid

  public ContactParameter getContactParameter() {
    return contactParameter;
  }

  public void setContactParameter(ContactParameter contactParameter) {
    this.contactParameter = contactParameter;
  }

  public ThirdParameter bankDetailsParameter(BankDetailsParameter bankDetailsParameter) {
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

  public ThirdParameter addressParameter(AddressParameter addressParameter) {
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
    ThirdParameter thirdParameter = (ThirdParameter) o;
    return Objects.equals(this.label, thirdParameter.label) &&
        Objects.equals(this.abbreviation, thirdParameter.abbreviation) &&
        Objects.equals(this.description, thirdParameter.description) &&
        Objects.equals(this.defaultCategoryId, thirdParameter.defaultCategoryId) &&
        Objects.equals(this.groupName, thirdParameter.groupName) &&
        Objects.equals(this.contactParameter, thirdParameter.contactParameter) &&
        Objects.equals(this.bankDetailsParameter, thirdParameter.bankDetailsParameter) &&
        Objects.equals(this.addressParameter, thirdParameter.addressParameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, abbreviation, description, defaultCategoryId, groupName, contactParameter, bankDetailsParameter, addressParameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ThirdParameter {\n");
    
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    defaultCategoryId: ").append(toIndentedString(defaultCategoryId)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    contactParameter: ").append(toIndentedString(contactParameter)).append("\n");
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

