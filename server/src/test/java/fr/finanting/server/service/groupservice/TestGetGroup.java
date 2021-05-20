package fr.finanting.server.service.groupservice;

import fr.finanting.server.codegen.model.GroupDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestGetGroup extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupServiceImpl groupServiceImpl;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupServiceImpl = new GroupServiceImpl(this.userRepository, this.groupRepository);
    }

    @Test
    public void testGetGroup() throws UserNotInGroupException, GroupNotExistException {
        final Group group = this.testFactory.getGroup();
        final User user = group.getUserAdmin();

        final GroupDTO groupDTO = this.groupServiceImpl.getGroup(group.getId(), user.getUserName());
        Assertions.assertEquals(group.getGroupName(), groupDTO.getGroupName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(group.getUserAdmin().getUserName(), groupDTO.getGroupUsers().get(0).getUserName());

    }

    @Test
    public void testGetGroupUserNotInGroup() {
        final Group group = this.testFactory.getGroup();

        final User user2 = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
                () -> this.groupServiceImpl.getGroup(group.getId(), user2.getUserName()));

    }

    @Test
    public void testGetGroupNotExist() {
        final User user = this.testFactory.getUser();

        Assertions.assertThrows(GroupNotExistException.class,
                () -> this.groupServiceImpl.getGroup(this.testFactory.getRandomInteger(), user.getUserName()));

    }

}
