package fr.finanting.server.controller;

import fr.finanting.server.generated.api.CategoryApi;
import fr.finanting.server.generated.model.CategoryParameter;
import fr.finanting.server.generated.model.TreeCategoryDTO;
import fr.finanting.server.generated.model.UpdateCategoryParameter;
import fr.finanting.server.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController extends MotherController implements CategoryApi {

    protected final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        super();
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<Void> createCategory(final CategoryParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.createCategory(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(final Integer categoryId) {
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.deleteCategory(categoryId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TreeCategoryDTO>> getGroupCategories(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        final List<TreeCategoryDTO> treeCategoryDTOList = this.categoryService.getGroupCategory(groupId, userName);
        return new ResponseEntity<>(treeCategoryDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TreeCategoryDTO>> getUserCategories() {
        final String userName = this.getCurrentPrincipalName();
        final List<TreeCategoryDTO> treeCategoryDTOList = this.categoryService.getUserCategory(userName);
        return new ResponseEntity<>(treeCategoryDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCategory(final Integer categoryId, final UpdateCategoryParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.updateCategory(categoryId, body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
