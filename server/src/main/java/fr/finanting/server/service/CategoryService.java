package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.dto.TreeCategoriesDTO;
import fr.finanting.server.exception.BadAssociationCategoryTypeException;
import fr.finanting.server.exception.BadAssociationCategoryUserGroupException;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.DeleteCategoryWithChildException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateCategoryParameter;
import fr.finanting.server.parameter.DeleteCategoryParameter;
import fr.finanting.server.parameter.UpdateCategoryParameter;

public interface CategoryService {

    public void createCategory(CreateCategoryParameter createCategoryParameter, String userName)
        throws CategoryNotExistException, BadAssociationCategoryUserGroupException, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryTypeException;

    public void updateCategory(UpdateCategoryParameter updateCategoryParameter, String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroupException, BadAssociationCategoryTypeException;

    public void deleteCategory(DeleteCategoryParameter deleteCategoryParameter, String userName) throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, DeleteCategoryWithChildException;

    public List<TreeCategoriesDTO> getUserCategory(String userName);

    public List<TreeCategoriesDTO> getGroupCategory(String groupName, String userName)
        throws GroupNotExistException, UserNotInGroupException;
    
}
