package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CategoryParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-25T17:50:48.110612+02:00[Europe/Paris]")
public class CategoryParameter   {
  @JsonProperty("parentId")
  private Integer parentId;

  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("description")
  private String description;

  @JsonProperty("groupName")
  private String groupName;

  /**
   * Gets or Sets categoryType
   */
  public enum CategoryTypeEnum {
    EXPENSE("EXPENSE"),
    
    REVENUE("REVENUE");

    private String value;

    CategoryTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CategoryTypeEnum fromValue(String value) {
      for (CategoryTypeEnum b : CategoryTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("categoryType")
  private CategoryTypeEnum categoryType;

  public CategoryParameter parentId(Integer parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * Get parentId
   * @return parentId
  */
  @ApiModelProperty(value = "")


  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public CategoryParameter label(String label) {
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

  public CategoryParameter abbreviation(String abbreviation) {
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

  public CategoryParameter description(String description) {
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

  public CategoryParameter groupName(String groupName) {
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

  public CategoryParameter categoryType(CategoryTypeEnum categoryType) {
    this.categoryType = categoryType;
    return this;
  }

  /**
   * Get categoryType
   * @return categoryType
  */
  @ApiModelProperty(value = "")


  public CategoryTypeEnum getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(CategoryTypeEnum categoryType) {
    this.categoryType = categoryType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryParameter categoryParameter = (CategoryParameter) o;
    return Objects.equals(this.parentId, categoryParameter.parentId) &&
        Objects.equals(this.label, categoryParameter.label) &&
        Objects.equals(this.abbreviation, categoryParameter.abbreviation) &&
        Objects.equals(this.description, categoryParameter.description) &&
        Objects.equals(this.groupName, categoryParameter.groupName) &&
        Objects.equals(this.categoryType, categoryParameter.categoryType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parentId, label, abbreviation, description, groupName, categoryType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategoryParameter {\n");
    
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    categoryType: ").append(toIndentedString(categoryType)).append("\n");
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

