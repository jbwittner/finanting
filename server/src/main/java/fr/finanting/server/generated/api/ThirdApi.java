/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package fr.finanting.server.generated.api;

import fr.finanting.server.generated.model.ThirdDTO;
import fr.finanting.server.generated.model.ThirdParameter;
import fr.finanting.server.generated.model.UpdateThirdParameter;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-13T16:22:45.636055+02:00[Europe/Paris]")
@Validated
@Api(value = "third", description = "the third API")
public interface ThirdApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /third : aaaaaaaaaaaaa
     *
     * @param thirdParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 201)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "createThird", notes = "", tags={ "third", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "aaaaaaaaaaaaa") })
    @PutMapping(
        value = "/third",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> createThird(@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) ThirdParameter thirdParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /third/{thirdId} : aaaaaaaaaaaaa
     *
     * @param thirdId aaaaaaaaaaaaa (required)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "deleteThird", notes = "", tags={ "third", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @DeleteMapping(
        value = "/third/{thirdId}"
    )
    default ResponseEntity<Void> deleteThird(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("thirdId") Integer thirdId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /third/{groupId} : aaaaaaaaaaaaa
     *
     * @param groupId aaaaaaaaaaaaa (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getGroupThird", notes = "", response = ThirdDTO.class, responseContainer = "List", tags={ "third", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = ThirdDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/third/{groupId}",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ThirdDTO>> getGroupThird(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("groupId") Integer groupId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /third : aaaaaaaaaaaaa
     *
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getUserThird", notes = "", response = ThirdDTO.class, responseContainer = "List", tags={ "third", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = ThirdDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/third",
        produces = { "application/json" }
    )
    default ResponseEntity<List<ThirdDTO>> getUserThird() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /third/{thirdId} : aaaaaaaaaaaaa
     *
     * @param thirdId aaaaaaaaaaaaa (required)
     * @param updateThirdParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "updateThird", notes = "", tags={ "third", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @PostMapping(
        value = "/third/{thirdId}",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateThird(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("thirdId") Integer thirdId,@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) UpdateThirdParameter updateThirdParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
