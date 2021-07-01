package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TreeCategoryDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-28T23:19:28.730960+02:00[Europe/Paris]")
public class TreeCategoryDTO   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("label")
  private String label;

  @JsonProperty("abbreviation")
  private String abbreviation;

  @JsonProperty("description")
  private String description;

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

  @JsonProperty("childTreeCategoriesDTOs")
  @Valid
  private List<TreeCategoryDTO> childTreeCategoriesDTOs = null;

  public TreeCategoryDTO id(Integer id) {
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

  public TreeCategoryDTO label(String label) {
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

  public TreeCategoryDTO abbreviation(String abbreviation) {
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

  public TreeCategoryDTO description(String description) {
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

  public TreeCategoryDTO categoryType(CategoryTypeEnum categoryType) {
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

  public TreeCategoryDTO childTreeCategoriesDTOs(List<TreeCategoryDTO> childTreeCategoriesDTOs) {
    this.childTreeCategoriesDTOs = childTreeCategoriesDTOs;
    return this;
  }

  public TreeCategoryDTO addChildTreeCategoriesDTOsItem(TreeCategoryDTO childTreeCategoriesDTOsItem) {
    if (this.childTreeCategoriesDTOs == null) {
      this.childTreeCategoriesDTOs = new ArrayList<TreeCategoryDTO>();
    }
    this.childTreeCategoriesDTOs.add(childTreeCategoriesDTOsItem);
    return this;
  }

  /**
   * Get childTreeCategoriesDTOs
   * @return childTreeCategoriesDTOs
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<TreeCategoryDTO> getChildTreeCategoriesDTOs() {
    return childTreeCategoriesDTOs;
  }

  public void setChildTreeCategoriesDTOs(List<TreeCategoryDTO> childTreeCategoriesDTOs) {
    this.childTreeCategoriesDTOs = childTreeCategoriesDTOs;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TreeCategoryDTO treeCategoryDTO = (TreeCategoryDTO) o;
    return Objects.equals(this.id, treeCategoryDTO.id) &&
        Objects.equals(this.label, treeCategoryDTO.label) &&
        Objects.equals(this.abbreviation, treeCategoryDTO.abbreviation) &&
        Objects.equals(this.description, treeCategoryDTO.description) &&
        Objects.equals(this.categoryType, treeCategoryDTO.categoryType) &&
        Objects.equals(this.childTreeCategoriesDTOs, treeCategoryDTO.childTreeCategoriesDTOs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, abbreviation, description, categoryType, childTreeCategoriesDTOs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TreeCategoryDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    abbreviation: ").append(toIndentedString(abbreviation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    categoryType: ").append(toIndentedString(categoryType)).append("\n");
    sb.append("    childTreeCategoriesDTOs: ").append(toIndentedString(childTreeCategoriesDTOs)).append("\n");
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

