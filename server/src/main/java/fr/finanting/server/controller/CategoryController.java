package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.CategoryApi;
import fr.finanting.server.codegen.model.CategoryDTO;
import fr.finanting.server.codegen.model.CategoryParameter;
import fr.finanting.server.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController extends MotherController implements CategoryApi {

    protected final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<Void> createCategory(CategoryParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.createCategory(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer categoryId) {
        return null;
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return null;
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getGroupCategories(Integer groupId) {
        return null;
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(CategoryParameter body) {
        return null;
    }

    /*
    @PostMapping("/createCategory")
    public void createCategory(final Authentication authentication,
                                    @RequestBody final CreateCategoryParameter createCategoryParameter)
            throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException  {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.categoryService.createCategory(createCategoryParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/updateCategory")
    public void updateCategory(final Authentication authentication,
                                    @RequestBody final UpdateCategoryParameter updateCategoryParameter)
            throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException  {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.categoryService.updateCategory(updateCategoryParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteCategory")
    public void deleteCategory(final Authentication authentication,
                                @RequestBody final DeleteCategoryParameter deleteCategoryParameter)
            throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.categoryService.deleteCategory(deleteCategoryParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getGroupCategory/{groupName}")
    public List<TreeCategoriesDTO> getGroupCategory(final Authentication authentication, 
                                            @PathVariable final String groupName)
            throws GroupNotExistException, UserNotInGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.categoryService.getGroupCategory(groupName, userDetailsImpl.getUsername());
    }

    @GetMapping("/getUserCategory")
    public List<TreeCategoriesDTO> getUserCategory(final Authentication authentication) {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.categoryService.getUserCategory(userDetailsImpl.getUsername());
    }

     */
    
}
