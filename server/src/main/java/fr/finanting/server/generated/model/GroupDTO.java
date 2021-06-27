package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.finanting.server.generated.model.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GroupDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-27T23:54:16.397484+02:00[Europe/Paris]")
public class GroupDTO   {
  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("userAdmin")
  private UserDTO userAdmin;

  @JsonProperty("groupUsers")
  @Valid
  private List<UserDTO> groupUsers = null;

  public GroupDTO groupName(String groupName) {
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

  public GroupDTO userAdmin(UserDTO userAdmin) {
    this.userAdmin = userAdmin;
    return this;
  }

  /**
   * Get userAdmin
   * @return userAdmin
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserDTO getUserAdmin() {
    return userAdmin;
  }

  public void setUserAdmin(UserDTO userAdmin) {
    this.userAdmin = userAdmin;
  }

  public GroupDTO groupUsers(List<UserDTO> groupUsers) {
    this.groupUsers = groupUsers;
    return this;
  }

  public GroupDTO addGroupUsersItem(UserDTO groupUsersItem) {
    if (this.groupUsers == null) {
      this.groupUsers = new ArrayList<UserDTO>();
    }
    this.groupUsers.add(groupUsersItem);
    return this;
  }

  /**
   * Get groupUsers
   * @return groupUsers
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<UserDTO> getGroupUsers() {
    return groupUsers;
  }

  public void setGroupUsers(List<UserDTO> groupUsers) {
    this.groupUsers = groupUsers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupDTO groupDTO = (GroupDTO) o;
    return Objects.equals(this.groupName, groupDTO.groupName) &&
        Objects.equals(this.userAdmin, groupDTO.userAdmin) &&
        Objects.equals(this.groupUsers, groupDTO.groupUsers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, userAdmin, groupUsers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupDTO {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    userAdmin: ").append(toIndentedString(userAdmin)).append("\n");
    sb.append("    groupUsers: ").append(toIndentedString(groupUsers)).append("\n");
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

