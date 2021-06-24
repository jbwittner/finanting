package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.AddressDTO;
import fr.finanting.server.generated.model.BankDetailsDTO;
import fr.finanting.server.generated.model.CategoryDTO;
import fr.finanting.server.generated.model.ContactDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ThirdDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-24T15:20:15.128749+02:00[Europe/Paris]")
public class ThirdDTO   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("description")
  private String description;

  @JsonProperty("categoryDTO")
  private CategoryDTO categoryDTO;

  @JsonProperty("bankDetailsDTO")
  private BankDetailsDTO bankDetailsDTO;

  @JsonProperty("addressDTO")
  private AddressDTO addressDTO;

  @JsonProperty("contactDTO")
  private ContactDTO contactDTO;

  public ThirdDTO id(Integer id) {
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

  public ThirdDTO label(String label) {
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

  public ThirdDTO abbreviation(String abbreviation) {
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

  public ThirdDTO description(String description) {
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

  public ThirdDTO categoryDTO(CategoryDTO categoryDTO) {
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

  public ThirdDTO bankDetailsDTO(BankDetailsDTO bankDetailsDTO) {
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

  public ThirdDTO addressDTO(AddressDTO addressDTO) {
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

  public ThirdDTO contactDTO(ContactDTO contactDTO) {
    this.contactDTO = contactDTO;
    return this;
  }

  /**
   * Get contactDTO
   * @return contactDTO
  */
  @ApiModelProperty(value = "")

  @Valid

  public ContactDTO getContactDTO() {
    return contactDTO;
  }

  public void setContactDTO(ContactDTO contactDTO) {
    this.contactDTO = contactDTO;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThirdDTO thirdDTO = (ThirdDTO) o;
    return Objects.equals(this.id, thirdDTO.id) &&
        Objects.equals(this.label, thirdDTO.label) &&
        Objects.equals(this.abbreviation, thirdDTO.abbreviation) &&
        Objects.equals(this.description, thirdDTO.description) &&
        Objects.equals(this.categoryDTO, thirdDTO.categoryDTO) &&
        Objects.equals(this.bankDetailsDTO, thirdDTO.bankDetailsDTO) &&
        Objects.equals(this.addressDTO, thirdDTO.addressDTO) &&
        Objects.equals(this.contactDTO, thirdDTO.contactDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, abbreviation, description, categoryDTO, bankDetailsDTO, addressDTO, contactDTO);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ThirdDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    categoryDTO: ").append(toIndentedString(categoryDTO)).append("\n");
    sb.append("    bankDetailsDTO: ").append(toIndentedString(bankDetailsDTO)).append("\n");
    sb.append("    addressDTO: ").append(toIndentedString(addressDTO)).append("\n");
    sb.append("    contactDTO: ").append(toIndentedString(contactDTO)).append("\n");
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

