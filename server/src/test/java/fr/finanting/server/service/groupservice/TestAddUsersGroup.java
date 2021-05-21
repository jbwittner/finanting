package fr.finanting.server.service.groupservice;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.codegen.model.AddUsersGroupParameter;
import fr.finanting.server.codegen.model.GroupDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestAddUsersGroup extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupServiceImpl groupServiceImpl;
    private Group group;

    @Override
    protected void initDataBeforeEach() {
        this.groupServiceImpl = new GroupServiceImpl(this.userRepository, this.groupRepository);
        this.group = this.testFactory.getGroup();
    }

    @Test
    public void testAddUserOk() throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final AddUsersGroupParameter addUsersGroupParameter = new AddUsersGroupParameter();
        addUsersGroupParameter.setGroupName(this.group.getGroupName());
        final List<String> userList = new ArrayList<>();
        final User user = this.testFactory.getUser();
        userList.add(user.getUserName());
        addUsersGroupParameter.setUsersName(userList);
        
        final GroupDTO groupDTO = this.groupServiceImpl.addUsersGroup(addUsersGroupParameter, this.group.getUserAdmin().getUserName());

        Assertions.assertEquals(2, this.group.getUsers().size());

        final List<User> users = this.group.getUsers();

        boolean isInGroup = false;

        for(final User userInGroup : users){
            if(userInGroup.getUserName().equals(user.getUserName())){
                isInGroup = true;
                break;
            }
        }

        Assertions.assertTrue(isInGroup);

        Assertions.assertEquals(this.group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(this.group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(this.group.getUserAdmin().getUserName(), groupDTO.getGroupUsers().get(0).getUserName());
        Assertions.assertEquals(user.getUserName(), groupDTO.getGroupUsers().get(1).getUserName());

    }

    @Test
    public void testAddUserAlreadyInGroupOk() throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final AddUsersGroupParameter addUsersGroupParameter = new AddUsersGroupParameter();
        addUsersGroupParameter.setGroupName(this.group.getGroupName());
        final List<String> userList = new ArrayList<>();
        userList.add(this.group.getUserAdmin().getUserName());
        addUsersGroupParameter.setUsersName(userList);
        
        final GroupDTO groupDTO = this.groupServiceImpl.addUsersGroup(addUsersGroupParameter, this.group.getUserAdmin().getUserName());

        Assertions.assertEquals(1, this.group.getUsers().size());

        Assertions.assertEquals(this.group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(this.group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(this.group.getUserAdmin().getUserName(), groupDTO.getGroupUsers().get(0).getUserName());

    }

    @Test
    public void testNotAdminExeptionOk() throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final AddUsersGroupParameter addUsersGroupParameter = new AddUsersGroupParameter();
        addUsersGroupParameter.setGroupName(this.group.getGroupName());

        final User user = this.testFactory.getUser();

        Assertions.assertThrows(NotAdminGroupException.class,
            () -> this.groupServiceImpl.addUsersGroup(addUsersGroupParameter, user.getUserName()));

    }

    @Test
    public void testGroupNoeExistOk() throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final AddUsersGroupParameter addUsersGroupParameter = new AddUsersGroupParameter();
        addUsersGroupParameter.setGroupName(this.faker.company().name());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.groupServiceImpl.addUsersGroup(addUsersGroupParameter, this.group.getUserAdmin().getUserName()));

    }

    @Test
    public void testUserNotExistExceptionOk() throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final AddUsersGroupParameter addUsersGroupParameter = new AddUsersGroupParameter();
        addUsersGroupParameter.setGroupName(this.group.getGroupName());
        final List<String> userList = new ArrayList<>();
        userList.add(this.faker.name().username());
        addUsersGroupParameter.setUsersName(userList);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupServiceImpl.addUsersGroup(addUsersGroupParameter, this.group.getUserAdmin().getUserName()));

    }
    
}
