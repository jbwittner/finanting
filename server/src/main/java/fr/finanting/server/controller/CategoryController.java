package fr.finanting.server.controller;

import fr.finanting.server.dto.CategoryDTO;
import fr.finanting.server.exception.*;
import fr.finanting.server.parameter.CreateCategoryParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.CategoryService;

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
    public CategoryDTO createCategory(final Authentication authentication,
                                    @RequestBody final CreateCategoryParameter createCategoryParameter)
            throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException  {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return categoryService.createCategory(createCategoryParameter, userDetailsImpl.getUsername());
    }
    
}
