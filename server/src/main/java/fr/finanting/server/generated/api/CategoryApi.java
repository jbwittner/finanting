/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package fr.finanting.server.generated.api;

import fr.finanting.server.generated.model.CategoryParameter;
import fr.finanting.server.generated.model.TreeCategoryDTO;
import fr.finanting.server.generated.model.UpdateCategoryParameter;
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
@Api(value = "category", description = "the category API")
public interface CategoryApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /category : aaaaaaaaaaaaa
     *
     * @param categoryParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 201)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "createCategory", notes = "", tags={ "category", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "aaaaaaaaaaaaa") })
    @PutMapping(
        value = "/category",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> createCategory(@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) CategoryParameter categoryParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /category/{categoryId} : aaaaaaaaaaaaa
     *
     * @param categoryId aaaaaaaaaaaaa (required)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "deleteCategory", notes = "", tags={ "category", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @DeleteMapping(
        value = "/category/{categoryId}"
    )
    default ResponseEntity<Void> deleteCategory(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("categoryId") Integer categoryId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /category/{groupId} : aaaaaaaaaaaaa
     *
     * @param groupId aaaaaaaaaaaaa (required)
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getGroupCategories", notes = "", response = TreeCategoryDTO.class, responseContainer = "List", tags={ "category", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = TreeCategoryDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/category/{groupId}",
        produces = { "application/json" }
    )
    default ResponseEntity<List<TreeCategoryDTO>> getGroupCategories(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("groupId") Integer groupId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"categoryType\" : \"EXPENSE\", \"childTreeCategoriesDTOs\" : [ null, null ], \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /category : aaaaaaaaaaaaa
     *
     * @return successful operation (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "getUserCategories", notes = "", response = TreeCategoryDTO.class, responseContainer = "List", tags={ "category", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = TreeCategoryDTO.class, responseContainer = "List") })
    @GetMapping(
        value = "/category",
        produces = { "application/json" }
    )
    default ResponseEntity<List<TreeCategoryDTO>> getUserCategories() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"categoryType\" : \"EXPENSE\", \"childTreeCategoriesDTOs\" : [ null, null ], \"description\" : \"description\", \"id\" : 0, \"label\" : \"label\", \"abbreviation\" : \"abbreviation\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /category/{categoryId} : aaaaaaaaaaaaa
     *
     * @param categoryId aaaaaaaaaaaaa (required)
     * @param updateCategoryParameter aaaaaaaaaaaaa (optional)
     * @return aaaaaaaaaaaaa (status code 200)
     */
    @ApiOperation(value = "aaaaaaaaaaaaa", nickname = "updateCategory", notes = "", tags={ "category", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "aaaaaaaaaaaaa") })
    @PostMapping(
        value = "/category/{categoryId}",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateCategory(@ApiParam(value = "aaaaaaaaaaaaa",required=true) @PathVariable("categoryId") Integer categoryId,@ApiParam(value = "aaaaaaaaaaaaa"  )  @Valid @RequestBody(required = false) UpdateCategoryParameter updateCategoryParameter) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
