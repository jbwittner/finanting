package fr.finanting.server.service.groupservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestDeleteGroup extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private GroupServiceImpl groupServiceImpl;
    private Group group;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupServiceImpl = new GroupServiceImpl(this.userRepository, this.groupRepository);
        this.group = this.testFactory.getGroup();
    }

    @Test
    public void testDeleteGroupOk() throws GroupNotExistException, NotAdminGroupException {
        Integer groupId = this.group.getId();
        final String userName = this.group.getUserAdmin().getUserName();
        this.groupServiceImpl.deleteGroup(groupId, userName);

        final Optional<Group> optionalGroup = this.groupRepository.findByGroupName(this.group.getGroupName());
        Assertions.assertFalse(optionalGroup.isPresent());
    }

    @Test
    public void testDeleteNonExistentGroup() throws GroupNotExistException, NotAdminGroupException {
        Integer groupId = this.group.getId() + 1;
        final String userName = this.group.getUserAdmin().getUserName();

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.groupServiceImpl.deleteGroup(groupId, userName));

    }

    @Test
    public void testDeleteNonAdminGroup() throws GroupNotExistException, NotAdminGroupException {
        Integer groupId = this.group.getId();
        final User user = this.testFactory.getUser();
        final String userName = user.getUserName();

        Assertions.assertThrows(NotAdminGroupException.class,
            () -> this.groupServiceImpl.deleteGroup(groupId, userName));

    }
}
