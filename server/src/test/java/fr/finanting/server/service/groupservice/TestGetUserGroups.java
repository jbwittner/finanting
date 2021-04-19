package fr.finanting.server.service.groupservice;

import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestGetUserGroups extends AbstractMotherIntegrationTest {

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
    public void testGetUserGroupsWithGroups() {
        final int numberGroups = 10;

        final User user = this.factory.getUser();
        final List<Group> groups = new ArrayList<>();

        for(int index = 0; index < numberGroups; index ++){
            Group group = this.factory.getGroup();
            this.userRepository.save(group.getUserAdmin());
            group = this.groupRepository.save(group);
            groups.add(group);
        }

        user.setGroups(groups);

        this.userRepository.save(user);

        final List<GroupDTO> groupsDTO = this.groupServiceImpl.getUserGroups(user.getUserName());

        Assertions.assertEquals(numberGroups, groupsDTO.size());

        for(final GroupDTO groupDTO : groupsDTO){

            boolean isPresent = false;
            Group groupPresent = new Group();

            for(final Group group : groups){
                if(group.getGroupName().equals(groupDTO.getGroupName())){
                    isPresent = true;
                    groupPresent = group;
                    break;
                }
            }

            Assertions.assertTrue(isPresent);
            Assertions.assertEquals(groupPresent.getGroupName(), groupDTO.getGroupName());
            Assertions.assertEquals(groupPresent.getUserAdmin().getUserName(), groupDTO.getUserAdmin().getUserName());
            Assertions.assertEquals(groupPresent.getUserAdmin().getUserName(),
                    groupDTO.getGroupUsers().get(0).getUserName());

        }

    }

    @Test
    public void testGetUserGroupsWithoutGroups() {

        final User user = this.factory.getUser();

        this.userRepository.save(user);

        final List<GroupDTO> groupsDTO = this.groupServiceImpl.getUserGroups(user.getUserName());

        Assertions.assertEquals(0, groupsDTO.size());

    }

}