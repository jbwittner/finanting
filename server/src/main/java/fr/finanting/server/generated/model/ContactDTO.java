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
 * ContactDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-28T23:19:28.730960+02:00[Europe/Paris]")
public class ContactDTO   {
  @JsonProperty("homePhone")
  private String homePhone;

  @JsonProperty("portablePhone")
  private String portablePhone;

  @JsonProperty("email")
  private String email;

  @JsonProperty("website")
  private String website;

  public ContactDTO homePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  /**
   * Get homePhone
   * @return homePhone
  */
  @ApiModelProperty(value = "")


  public String getHomePhone() {
    return homePhone;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  public ContactDTO portablePhone(String portablePhone) {
    this.portablePhone = portablePhone;
    return this;
  }

  /**
   * Get portablePhone
   * @return portablePhone
  */
  @ApiModelProperty(value = "")


  public String getPortablePhone() {
    return portablePhone;
  }

  public void setPortablePhone(String portablePhone) {
    this.portablePhone = portablePhone;
  }

  public ContactDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @ApiModelProperty(value = "")


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ContactDTO website(String website) {
    this.website = website;
    return this;
  }

  /**
   * Get website
   * @return website
  */
  @ApiModelProperty(value = "")


  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactDTO contactDTO = (ContactDTO) o;
    return Objects.equals(this.homePhone, contactDTO.homePhone) &&
        Objects.equals(this.portablePhone, contactDTO.portablePhone) &&
        Objects.equals(this.email, contactDTO.email) &&
        Objects.equals(this.website, contactDTO.website);
  }

  @Override
  public int hashCode() {
    return Objects.hash(homePhone, portablePhone, email, website);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactDTO {\n");
    
    sb.append("    homePhone: ").append(toIndentedString(homePhone)).append("\n");
    sb.append("    portablePhone: ").append(toIndentedString(portablePhone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    website: ").append(toIndentedString(website)).append("\n");
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

