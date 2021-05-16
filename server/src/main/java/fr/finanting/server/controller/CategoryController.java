package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.CategoryApi;
import fr.finanting.server.codegen.model.CategoryDTO;
import fr.finanting.server.codegen.model.CategoryParameter;
import fr.finanting.server.codegen.model.TreeCategoryDTO;
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
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.deleteCategory(categoryId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TreeCategoryDTO>> getGroupCategories(Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        List<TreeCategoryDTO> treeCategoryDTOList = this.categoryService.getGroupCategory(groupId, userName);
        return new ResponseEntity<>(treeCategoryDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TreeCategoryDTO>> getUserCategories() {
        final String userName = this.getCurrentPrincipalName();
        List<TreeCategoryDTO> treeCategoryDTOList = this.categoryService.getUserCategory(userName);
        return new ResponseEntity<>(treeCategoryDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCategory(Integer categoryId, CategoryParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.categoryService.updateCategory(categoryId, body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
