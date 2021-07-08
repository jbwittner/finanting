package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GroupParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-08T22:27:14.328456+02:00[Europe/Paris]")
public class GroupParameter   {
  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("usersName")
  @Valid
  private List<String> usersName = null;

  public GroupParameter groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * Get groupName
   * @return groupName
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public GroupParameter usersName(List<String> usersName) {
    this.usersName = usersName;
    return this;
  }

  public GroupParameter addUsersNameItem(String usersNameItem) {
    if (this.usersName == null) {
      this.usersName = new ArrayList<String>();
    }
    this.usersName.add(usersNameItem);
    return this;
  }

  /**
   * Get usersName
   * @return usersName
  */
  @ApiModelProperty(value = "")


  public List<String> getUsersName() {
    return usersName;
  }

  public void setUsersName(List<String> usersName) {
    this.usersName = usersName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupParameter groupParameter = (GroupParameter) o;
    return Objects.equals(this.groupName, groupParameter.groupName) &&
        Objects.equals(this.usersName, groupParameter.usersName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, usersName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupParameter {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    usersName: ").append(toIndentedString(usersName)).append("\n");
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

