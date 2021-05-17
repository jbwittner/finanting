package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.codegen.model.CategoryParameter;
import fr.finanting.server.codegen.model.TreeCategoryDTO;
import fr.finanting.server.codegen.model.UpdateCategoryParameter;
import fr.finanting.server.exception.BadAssociationCategoryTypeException;
import fr.finanting.server.exception.BadAssociationCategoryUserGroupException;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.DeleteCategoryWithChildException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;

public interface CategoryService {

    void createCategory(CategoryParameter categoryParameter, String userName)
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException;

    void updateCategory(Integer categoryId, UpdateCategoryParameter updateCategoryParameter, String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroupException, BadAssociationCategoryTypeException;

    void deleteCategory(Integer categoryId, String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException;

    List<TreeCategoryDTO> getUserCategory(String userName);

    List<TreeCategoryDTO> getGroupCategory(Integer groupId, String userName)
        throws GroupNotExistException, UserNotInGroupException;
    
}
