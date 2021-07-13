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
 * ExceptionDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-13T16:22:45.636055+02:00[Europe/Paris]")
public class ExceptionDTO   {
  @JsonProperty("details")
  private String details;

  @JsonProperty("exception")
  private String exception;

  @JsonProperty("message")
  private String message;

  @JsonProperty("timestamp")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private Date timestamp;

  public ExceptionDTO details(String details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   * @return details
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public ExceptionDTO exception(String exception) {
    this.exception = exception;
    return this;
  }

  /**
   * Get exception
   * @return exception
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public ExceptionDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ExceptionDTO timestamp(Date timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExceptionDTO exceptionDTO = (ExceptionDTO) o;
    return Objects.equals(this.details, exceptionDTO.details) &&
        Objects.equals(this.exception, exceptionDTO.exception) &&
        Objects.equals(this.message, exceptionDTO.message) &&
        Objects.equals(this.timestamp, exceptionDTO.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(details, exception, message, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExceptionDTO {\n");
    
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    exception: ").append(toIndentedString(exception)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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

