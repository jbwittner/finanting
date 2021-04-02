package fr.finanting.server.service;

import fr.finanting.server.dto.GroupingCategoriesDTO;
import fr.finanting.server.dto.UserCategoryDTO;
import fr.finanting.server.exception.BadAssociationCategoryType;
import fr.finanting.server.exception.BadAssociationCategoryUserGroup;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateCategoryParameter;
import fr.finanting.server.parameter.UpdateCategoryParameter;

public interface CategoryService {

    public void createCategory(CreateCategoryParameter createCategoryParameter, String userName)
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryType;

    public void updateCategory(UpdateCategoryParameter updateCategoryParameter, String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup, BadAssociationCategoryType;

    public UserCategoryDTO getAllUserCategory(String userName);

    public GroupingCategoriesDTO getUserCategory(String userName);

    public GroupingCategoriesDTO getGroupCategory(String groupName, String userName)
        throws GroupNotExistException, UserNotInGroupException;
    
}
