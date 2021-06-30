/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package fr.finanting.server.generated.api;

import fr.finanting.server.generated.model.BankingTransactionDTO;
import fr.finanting.server.generated.model.BankingTransactionParameter;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-25T17:50:48.110612+02:00[Europe/Paris]")
@Validated
@Api(value = "bankingTransaction", description = "the bankingTransaction API")
public interface BankingTransactionApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /bankingTransaction : aaaaaaaaaaaaa
     *
     * @param bankingTransactionParameter aaaaaaaaaaaaa (optional)
     * @return successful operation (status code 201)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "createBankingTransaction", notes = "", response = BankingTransactionDTO.class, tags={ "bankingTransaction", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successful operation", response = BankingTransactionDTO.class) })
    @PutMapping(
        value = "/bankingTransaction",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<BankingTransactionDTO> createBankingTransaction(@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) BankingTransactionParameter bankingTransactionParameter) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"amount\" : 6.027456183070403, \"currencyAmount\" : 1.4658129805029452, \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"description\" : \"description\", \"thirdDTO\" : { \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"transactionDate\" : \"2000-01-23\", \"amountDate\" : \"2000-01-23\", \"updateTimestamp\" : \"2000-01-23\", \"createTimestamp\" : \"2000-01-23\", \"bankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"linkedBankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"classificationDTO\" : { \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /bankingTransaction/{bankingTransactionId} : aaaaaaaaaaaaa
     *
     * @param bankingTransactionId aaaaaaaaaaaaa (required)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "deleteBankingTransaction", notes = "", tags={ "bankingTransaction", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @DeleteMapping(
        value = "/bankingTransaction/{bankingTransactionId}"
    )
    default ResponseEntity<Void> deleteBankingTransaction(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("bankingTransactionId") Integer bankingTransactionId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bankingTransaction/{bankingAccountId} : aaaaaaaaaaaaa
     *
     * @param bankingAccountId aaaaaaaaaaaaa (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getBankingAccountTransaction", notes = "", response = BankingTransactionDTO.class, responseContainer = "List", tags={ "bankingTransaction", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = BankingTransactionDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/bankingTransaction/{bankingAccountId}",
        produces = { "application/json" }
    )
    default ResponseEntity<List<BankingTransactionDTO>> getBankingAccountTransaction(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("bankingAccountId") Integer bankingAccountId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"amount\" : 6.027456183070403, \"currencyAmount\" : 1.4658129805029452, \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"description\" : \"description\", \"thirdDTO\" : { \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"transactionDate\" : \"2000-01-23\", \"amountDate\" : \"2000-01-23\", \"updateTimestamp\" : \"2000-01-23\", \"createTimestamp\" : \"2000-01-23\", \"bankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"linkedBankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"classificationDTO\" : { \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bankingTransaction/{bankingTransactionId} : aaaaaaaaaaaaa
     *
     * @param bankingTransactionId aaaaaaaaaaaaa (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getBankingTransaction", notes = "", response = BankingTransactionDTO.class, tags={ "bankingTransaction", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = BankingTransactionDTO.class) })
    @GetMapping(
        value = "/bankingTransaction/{bankingTransactionId}",
        produces = { "application/json" }
    )
    default ResponseEntity<BankingTransactionDTO> getBankingTransaction(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("bankingTransactionId") Integer bankingTransactionId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"amount\" : 6.027456183070403, \"currencyAmount\" : 1.4658129805029452, \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"description\" : \"description\", \"thirdDTO\" : { \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"transactionDate\" : \"2000-01-23\", \"amountDate\" : \"2000-01-23\", \"updateTimestamp\" : \"2000-01-23\", \"createTimestamp\" : \"2000-01-23\", \"bankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"linkedBankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"classificationDTO\" : { \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /bankingTransaction/{bankingTransactionId} : aaaaaaaaaaaaa
     *
     * @param bankingTransactionId aaaaaaaaaaaaa (required)
     * @param bankingTransactionParameter aaaaaaaaaaaaa (optional)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "updateBankingTransaction", notes = "", response = BankingTransactionDTO.class, tags={ "bankingTransaction", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = BankingTransactionDTO.class) })
    @PostMapping(
        value = "/bankingTransaction/{bankingTransactionId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<BankingTransactionDTO> updateBankingTransaction(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("bankingTransactionId") Integer bankingTransactionId,@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) BankingTransactionParameter bankingTransactionParameter) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"amount\" : 6.027456183070403, \"currencyAmount\" : 1.4658129805029452, \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"description\" : \"description\", \"thirdDTO\" : { \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"description\" : \"description\", \"categoryDTO\" : { \"categoryType\" : \"EXPENSE\", \"description\" : \"description\", \"id\" : 6, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"contactDTO\" : { \"website\" : \"website\", \"homePhone\" : \"homePhone\", \"email\" : \"email\", \"portablePhone\" : \"portablePhone\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"transactionDate\" : \"2000-01-23\", \"amountDate\" : \"2000-01-23\", \"updateTimestamp\" : \"2000-01-23\", \"createTimestamp\" : \"2000-01-23\", \"bankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"linkedBankingAccountDTO\" : { \"balance\" : 6.027456183070403, \"addressDTO\" : { \"zipCode\" : \"zipCode\", \"address\" : \"address\", \"city\" : \"city\", \"street\" : \"street\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"bankDetailsDTO\" : { \"iban\" : \"iban\", \"bankName\" : \"bankName\", \"accountNumber\" : \"accountNumber\" }, \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"classificationDTO\" : { \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }, \"currencyDTO\" : { \"symbol\" : \"symbol\", \"decimalPlaces\" : 1, \"isoCode\" : \"isoCode\", \"rate\" : 6, \"defaultCurrency\" : true, \"id\" : 0, \"label\" : \"label\" }, \"id\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}