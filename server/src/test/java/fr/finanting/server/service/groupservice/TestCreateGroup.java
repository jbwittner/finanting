package fr.finanting.server.service.groupservice;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.codegen.model.GroupDTO;
import fr.finanting.codegen.model.GroupParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.GroupNameAlreadyExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateGroup extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupServiceImpl groupServiceImpl;

    private User userPrincipal;
    private static final Integer NUMBER_ACCOUNT = 10;

    @Override
    protected void initDataBeforeEach() {
        this.groupServiceImpl = new GroupServiceImpl(this.userRepository, this.groupRepository);
        this.userPrincipal = this.testFactory.getUser();
    }

    @Test
    public void testCreateGroupWithoutUsers() throws GroupNameAlreadyExistException, UserNotExistException {
        final GroupParameter groupParameter = new GroupParameter();
        groupParameter.setGroupName(this.faker.company().name());
        groupParameter.setUsersName(new ArrayList<>());

        this.groupServiceImpl.createGroup(groupParameter, this.userPrincipal.getUserName());

        final Group group = this.groupRepository.findByGroupName(groupParameter.getGroupName()).orElseThrow();

        Assertions.assertEquals(groupParameter.getGroupName(), group.getGroupName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), group.getUserAdmin().getUserName());
        Assertions.assertEquals(1, group.getUsers().size());
        Assertions.assertEquals(this.userPrincipal.getUserName(), group.getUsers().get(0).getUserName());
    }

    @Test
    public void testCreateGroupWithUsers() throws GroupNameAlreadyExistException, UserNotExistException {
        final GroupParameter groupParameter = new GroupParameter();
        groupParameter.setGroupName(this.faker.company().name());

        final List<String> usersNameList = new ArrayList<>(); 

        for(int index = 0; index < NUMBER_ACCOUNT; index ++){
            final User user = this.testFactory.getUser();
            usersNameList.add(user.getUserName());
        }

        groupParameter.setUsersName(usersNameList);

        final GroupDTO groupDTO = this.groupServiceImpl.createGroup(groupParameter, this.userPrincipal.getUserName());

        final Group group = this.groupRepository.findByGroupName(groupParameter.getGroupName()).orElseThrow();

        Assertions.assertEquals(groupParameter.getGroupName(), group.getGroupName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), group.getUserAdmin().getUserName());
        Assertions.assertEquals(NUMBER_ACCOUNT + 1, group.getUsers().size());

        for(final String userNames : usersNameList){
            boolean userAdded = false;

            for(final User usersGroup : group.getUsers()){

                if (userNames.equals(usersGroup.getUserName())) {
                    userAdded = true;
                    break;
                }
    
            }

            Assertions.assertTrue(userAdded);

        }

        Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getGroupUsers().get(0).getUserName());

    }

    @Test
    public void testGroupNameAlreadyUsed() throws GroupNameAlreadyExistException, UserNotExistException{
        final Group group = this.testFactory.getGroup();

        final GroupParameter groupParameter = new GroupParameter();
        groupParameter.setGroupName(group.getGroupName());

        Assertions.assertThrows(GroupNameAlreadyExistException.class,
            () -> this.groupServiceImpl.createGroup(groupParameter, this.userPrincipal.getUserName()));
    }

    @Test
    public void testUserNotExist() throws GroupNameAlreadyExistException, UserNotExistException {
        final GroupParameter groupParameter = new GroupParameter();
        groupParameter.setGroupName(this.faker.company().name());

        final List<String> usersNameList = new ArrayList<>();
        usersNameList.add(this.faker.name().username());

        groupParameter.setUsersName(usersNameList);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupServiceImpl.createGroup(groupParameter, this.userPrincipal.getUserName()));
    }
    
}
