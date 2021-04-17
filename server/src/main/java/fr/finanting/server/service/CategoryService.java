package fr.finanting.server.service;

import fr.finanting.server.dto.GroupingCategoriesDTO;
import fr.finanting.server.dto.UserCategoryDTO;
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

    public UserCategoryDTO getAllUserCategory(String userName);

    public GroupingCategoriesDTO getUserCategory(String userName);

    public GroupingCategoriesDTO getGroupCategory(String groupName, String userName)
        throws GroupNotExistException, UserNotInGroupException;
    
}
