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
 * AddUsersGroupParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-13T16:22:45.636055+02:00[Europe/Paris]")
public class AddUsersGroupParameter   {
  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("usersName")
  @Valid
  private List<String> usersName = new ArrayList<String>();

  public AddUsersGroupParameter groupName(String groupName) {
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

  public AddUsersGroupParameter usersName(List<String> usersName) {
    this.usersName = usersName;
    return this;
  }

  public AddUsersGroupParameter addUsersNameItem(String usersNameItem) {
    this.usersName.add(usersNameItem);
    return this;
  }

  /**
   * Get usersName
   * @return usersName
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


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
    AddUsersGroupParameter addUsersGroupParameter = (AddUsersGroupParameter) o;
    return Objects.equals(this.groupName, addUsersGroupParameter.groupName) &&
        Objects.equals(this.usersName, addUsersGroupParameter.usersName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, usersName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddUsersGroupParameter {\n");
    
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

