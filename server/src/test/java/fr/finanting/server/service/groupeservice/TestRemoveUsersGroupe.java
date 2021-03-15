package fr.finanting.server.service.groupservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.RemoveUsersGroupParameter;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test removeUsersGroup method
 */
public class TestRemoveUsersGroup extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupServiceImpl groupServiceImpl;
    private Group group;

    private Integer NUMBER_USERS = 10;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupServiceImpl = new GroupServiceImpl(this.userRepository, this.groupRepository);
        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());
        final List<User> users = this.group.getUsers();
        for(Integer index = 0; index < NUMBER_USERS; index ++){
            final User user = this.userRepository.save(this.factory.getUser());
            users.add(user);
        }
        this.groupRepository.save(this.group);
    }

    /**
     * Test to remove one user from a group
     */
    @Test
    public void testRemoveOneUserGroupOK() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final List<User> users = this.group.getUsers();
        final User user = users.get(NUMBER_USERS);

        final List<String> usersName = new ArrayList<>();
        usersName.add(user.getUserName());

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.group.getGroupName());
        removeUsersGroupParameter.setUsersName(usersName);

        final GroupDTO groupDTO = this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, this.group.getUserAdmin().getUserName());

        final Group group = this.groupRepository.findByGroupName(removeUsersGroupParameter.getGroupName()).get();

        Assertions.assertEquals(NUMBER_USERS, group.getUsers().size());

        Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());

    }

    /**
     * Test to remove two users from a group
     */
    @Test
    public void testRemoveMultipleUserGroupOK() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final List<User> users = this.group.getUsers();

        final User userOne = users.get(NUMBER_USERS);
        final User userTwo = users.get(NUMBER_USERS - 1);

        final List<String> usersName = new ArrayList<>();
        usersName.add(userOne.getUserName());
        usersName.add(userTwo.getUserName());

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.group.getGroupName());
        removeUsersGroupParameter.setUsersName(usersName);

        final GroupDTO groupDTO = this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, this.group.getUserAdmin().getUserName());

        final Group group = this.groupRepository.findByGroupName(removeUsersGroupParameter.getGroupName()).get();

        Assertions.assertEquals(NUMBER_USERS - 1, group.getUsers().size());

        Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
    }

    /**
     * Test with a group that doesn't exist
     */
    @Test
    public void testGroupNotExist() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.faker.company().name());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, this.group.getUserAdmin().getUserName()));

    }

    /**
     * Test with a group who the user are not the admin
     */
    @Test
    public void testNotAdminGroup() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.group.getGroupName());

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotAdminGroupException.class,
            () -> this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, user.getUserName()));

    }

    /**
     * Test to remove a user that doesn't exist
     */
    @Test
    public void testUserNotExist() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final List<String> usersName = new ArrayList<>();
        usersName.add(this.faker.name().username());

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.group.getGroupName());
        removeUsersGroupParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, this.group.getUserAdmin().getUserName()));

    }

    /**
     * Test to remove a user who are not in the group
     */
    @Test
    public void testUserNotInGroup() throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final List<String> usersName = new ArrayList<>();
        final User user = this.userRepository.save(this.factory.getUser());
        usersName.add(user.getUserName());

        final RemoveUsersGroupParameter removeUsersGroupParameter = new RemoveUsersGroupParameter();
        removeUsersGroupParameter.setGroupName(this.group.getGroupName());
        removeUsersGroupParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.groupServiceImpl.removeUsersGroup(removeUsersGroupParameter, this.group.getUserAdmin().getUserName()));
            
    }
    
}
