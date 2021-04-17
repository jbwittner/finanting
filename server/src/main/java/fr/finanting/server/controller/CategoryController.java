package fr.finanting.server.controller;

import fr.finanting.server.dto.TreeCategoriesDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateCategoryParameter;
import fr.finanting.server.parameter.DeleteCategoryParameter;
import fr.finanting.server.parameter.UpdateCategoryParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    protected final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

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
    
}
