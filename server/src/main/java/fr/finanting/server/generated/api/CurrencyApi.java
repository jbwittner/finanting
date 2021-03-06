/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package fr.finanting.server.generated.api;

import fr.finanting.server.generated.model.CurrencyDTO;
import fr.finanting.server.generated.model.CurrencyParameter;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-28T23:19:28.730960+02:00[Europe/Paris]")
@Validated
@Api(value = "currency", description = "the currency API")
public interface CurrencyApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /currency : aaaaaaaaaaaaa
     *
     * @param currencyParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 201)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "createCurrency", notes = "", tags={ "currency", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "aaaaaaaaaaaaa") })
    @PutMapping(
        value = "/currency",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> createCurrency(@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) CurrencyParameter currencyParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /currency/{currencyId} : aaaaaaaaaaaaa
     *
     * @param currencyId aaaaaaaaaaaaa (required)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "deleteCurrency", notes = "", tags={ "currency", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @DeleteMapping(
        value = "/currency/{currencyId}"
    )
    default ResponseEntity<Void> deleteCurrency(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("currencyId") Integer currencyId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /currency : aaaaaaaaaaaaa
     *
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getAllCurrencies", notes = "", response = CurrencyDTO.class, responseContainer = "List", tags={ "currency", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = CurrencyDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/currency",
        produces = { "application/json" }
    )
    default ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /currency/{currencyId} : aaaaaaaaaaaaa
     *
     * @param currencyId aaaaaaaaaaaaa (required)
     * @param currencyParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "updateCurrency", notes = "", tags={ "currency", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @PostMapping(
        value = "/currency/{currencyId}",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateCurrency(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("currencyId") Integer currencyId,@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) CurrencyParameter currencyParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
