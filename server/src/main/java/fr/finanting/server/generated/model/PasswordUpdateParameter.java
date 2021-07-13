package fr.finanting.server.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PasswordUpdateParameter
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-13T16:22:45.636055+02:00[Europe/Paris]")
public class PasswordUpdateParameter   {
  @JsonProperty("previousPassword")
  private String previousPassword;

  @JsonProperty("newPassword")
  private String newPassword;

  public PasswordUpdateParameter previousPassword(String previousPassword) {
    this.previousPassword = previousPassword;
    return this;
  }

  /**
   * Get previousPassword
   * @return previousPassword
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPreviousPassword() {
    return previousPassword;
  }

  public void setPreviousPassword(String previousPassword) {
    this.previousPassword = previousPassword;
  }

  public PasswordUpdateParameter newPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

  /**
   * Get newPassword
   * @return newPassword
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PasswordUpdateParameter passwordUpdateParameter = (PasswordUpdateParameter) o;
    return Objects.equals(this.previousPassword, passwordUpdateParameter.previousPassword) &&
        Objects.equals(this.newPassword, passwordUpdateParameter.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(previousPassword, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PasswordUpdateParameter {\n");
    
    sb.append("    previousPassword: ").append(toIndentedString(previousPassword)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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

