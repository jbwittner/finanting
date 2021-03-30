package fr.finanting.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.finanting.server.dto.CategoryDTO;
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
    public CategoryDTO createCategory(CreateCategoryParameter createCategoryParameter, String userName)
        throws CategoryNotExistException, BadAssociationCategoryUserGroup, GroupNotExistException, CategoryNoUserException, UserNotInGroupException{

        Category category = new Category();

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        // check if the new category are a sub category
        if(createCategoryParameter.getParentId() != null){
            Integer id = createCategoryParameter.getParentId();
            Category parentCategory = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(id));

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

        CategoryDTO categoryDTO = new CategoryDTO(category);

        return categoryDTO;

    }

    @Override
    public CategoryDTO updateCategory(UpdateCategoryParameter updateCategoryParameter, String userName)
        throws CategoryNotExistException, CategoryNoUserException, UserNotInGroupException, BadAssociationCategoryUserGroup{

        Category category = this.categoryRepository.findById(updateCategoryParameter.getId())
            .orElseThrow(() -> new CategoryNotExistException(updateCategoryParameter.getId()));

        final User user = this.userRepository.findByUserName(userName).orElseThrow();

        if(updateCategoryParameter.getParentId() != null){
            Integer id = updateCategoryParameter.getParentId();
            Category parentCategory = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotExistException(id));

            boolean areGoodAssociation = false;

            if(parentCategory.getUser() != null){
                if(parentCategory.getUser().getUserName().equals(userName)
                && parentCategory.getUser().getUserName().equals(category.getUser().getUserName())){
                    areGoodAssociation = true;
                } else {
                    throw new CategoryNoUserException(id);
                }
            } else if(parentCategory.getGroup() != null) {

                parentCategory.getGroup().checkAreInGroup(user);
                category.getGroup().checkAreInGroup(user);

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

        CategoryDTO categoryDTO = new CategoryDTO(category);

        return categoryDTO;

    }

}
