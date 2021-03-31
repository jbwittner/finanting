package fr.finanting.server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.CategoryDTO;
import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.dto.GroupingCategoriesDTO;
import fr.finanting.server.dto.TreeCategoriesDTO;
import fr.finanting.server.dto.UserCategoryDTO;
import fr.finanting.server.exception.BadAssociationCategoryUserGroup;
import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Category;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateCategoryParameter;
import fr.finanting.server.parameter.UpdateCategoryParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(final UserRepository userRepository, final GroupRepository groupRepository, final CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO createCategory(final CreateCategoryParameter createCategoryParameter, final String userName)
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException{

        Category category = new Category();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        // check if the new category are a sub category
        if(createCategoryParameter.getParentId() != null){
            final Integer id = createCategoryParameter.getParentId();
            final Category parentCategory = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(id));

            boolean areGoodAssociation = false;

            if(parentCategory.getGroup() != null && createCategoryParameter.getGroupName() != null){
                parentCategory.getGroup().checkAreInGroup(user);

                if(parentCategory.getGroup().getGroupName().equals(createCategoryParameter.getGroupName())){
                    areGoodAssociation = true;
                }

            } else if(parentCategory.getUser() != null  && createCategoryParameter.getGroupName() == null){
                if(parentCategory.getUser().getUserName().equals(userName)){
                    areGoodAssociation = true;
                } else {
                    throw new CategoryNoUserException(id);
                }
            }

            if(!areGoodAssociation){
                throw new BadAssociationCategoryUserGroup();
            }

            category.setParent(parentCategory);

        }

        category.setLabel(createCategoryParameter.getLabel());
        category.setAbbreviation(createCategoryParameter.getAbbreviation().toUpperCase());
        category.setDescritpion(createCategoryParameter.getDescritpion());
        category.setCategoryType(createCategoryParameter.getCategoryType());

        if(createCategoryParameter.getGroupName() == null){
            category.setUser(user);
        } else {
            final String groupName = createCategoryParameter.getGroupName();
            final Group group = this.groupRepository.findByGroupName(groupName)
                .orElseThrow(() -> new GroupNotExistException(groupName));
            category.setGroup(group);
        }

        category = this.categoryRepository.save(category);

        final CategoryDTO categoryDTO = new CategoryDTO(category);

        return categoryDTO;

    }

    @Override
    public CategoryDTO updateCategory(final UpdateCategoryParameter updateCategoryParameter, final String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup{

        final Category category = this.categoryRepository.findById(updateCategoryParameter.getId())
            .orElseThrow(() -> new CategoryNotExistException(updateCategoryParameter.getId()));

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        if(category.getGroup() != null){
            category.getGroup().checkAreInGroup(user);
        } else if (!category.getUser().getUserName().equals(userName)){
            throw new CategoryNoUserException(updateCategoryParameter.getId());
        }

        if(updateCategoryParameter.getParentId() == null){
            category.setParent(null);
        } else {
            final Integer id = updateCategoryParameter.getParentId();
            final Category parentCategory = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(id));

            boolean areGoodAssociation = false;

            if(parentCategory.getUser() != null && category.getUser() != null){
                if(parentCategory.getUser().getUserName().equals(userName)){
                    areGoodAssociation = true;
                } else {
                    throw new CategoryNoUserException(id);
                }
            } else if(parentCategory.getGroup() != null && category.getGroup() != null) {

                parentCategory.getGroup().checkAreInGroup(user);

                if(parentCategory.getGroup().getGroupName().equals(category.getGroup().getGroupName())){
                    areGoodAssociation = true;
                }
            }

            if(!areGoodAssociation){
                throw new BadAssociationCategoryUserGroup();
            }

            category.setParent(parentCategory);

        }

        category.setLabel(updateCategoryParameter.getLabel());
        category.setAbbreviation(updateCategoryParameter.getAbbreviation().toUpperCase());
        category.setDescritpion(updateCategoryParameter.getDescritpion());
        category.setCategoryType(updateCategoryParameter.getCategoryType());

        final CategoryDTO categoryDTO = new CategoryDTO(category);

        return categoryDTO;

    }

    @Override
    public UserCategoryDTO getUserCategory(final String userName) {

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        List<Category> motherCategories = this.categoryRepository.findByUserAndGroupIsNullAndParentIsNull(user);

        final UserCategoryDTO userCategoryDTO = new UserCategoryDTO();
        final List<GroupingCategoriesDTO> groupingCategoriesDTOs = new ArrayList<>();

        // User categories
        final GroupingCategoriesDTO userGroupingCategoriesDTO = new GroupingCategoriesDTO();
        final List<TreeCategoriesDTO> userTreeCategoriesDTOs = new ArrayList<>();
        
        for(final Category motherCategory : motherCategories){
            final TreeCategoriesDTO treeCategoriesDTO = new TreeCategoriesDTO(motherCategory);
            userTreeCategoriesDTOs.add(treeCategoriesDTO);
        }
        userGroupingCategoriesDTO.setTreeCategoriesDTOs(userTreeCategoriesDTOs);
        groupingCategoriesDTOs.add(userGroupingCategoriesDTO);

        // Groups categories
        final List<Group> groups = user.getGroups();

        for(final Group group : groups){
            motherCategories = this.categoryRepository.findByUserAndGroupAndParentIsNull(user, group);

            final GroupingCategoriesDTO groupGroupingCategoriesDTO = new GroupingCategoriesDTO();
            final List<TreeCategoriesDTO> groupTreeCategoriesDTOs = new ArrayList<>();

            for(final Category motherCategory : motherCategories){
                final TreeCategoriesDTO treeCategoriesDTO = new TreeCategoriesDTO(motherCategory);
                groupTreeCategoriesDTOs.add(treeCategoriesDTO);
            }

            groupGroupingCategoriesDTO.setGroupName(group.getGroupName());
            groupGroupingCategoriesDTO.setTreeCategoriesDTOs(groupTreeCategoriesDTOs);
            groupingCategoriesDTOs.add(groupGroupingCategoriesDTO);
        }

        userCategoryDTO.setGroupingCategoriesDTOs(groupingCategoriesDTOs);

        return userCategoryDTO;
    }

}
