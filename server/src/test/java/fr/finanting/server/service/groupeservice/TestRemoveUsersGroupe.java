package fr.finanting.server.service.groupeservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.GroupeDTO;
import fr.finanting.server.exception.GroupeNotExistException;
import fr.finanting.server.exception.NotAdminGroupeException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupeException;
import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.RemoveUsersGroupeParameter;
import fr.finanting.server.repository.GroupeRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupeServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test removeUsersGroupe method
 */
public class TestRemoveUsersGroupe extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    private GroupeServiceImpl groupeServiceImpl;
    private Groupe groupe;

    private Integer NUMBER_USERS = 10;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupeServiceImpl = new GroupeServiceImpl(this.userRepository, this.groupeRepository);
        this.groupe = this.factory.getGroupe();
        this.userRepository.save(this.groupe.getUserAdmin());
        final List<User> users = this.groupe.getUsers();
        for(Integer index = 0; index < NUMBER_USERS; index ++){
            final User user = this.userRepository.save(this.factory.getUser());
            users.add(user);
        }
        this.groupeRepository.save(this.groupe);
    }

    /**
     * Test to remove one user from a groupe
     */
    @Test
    public void testRemoveOneUserGroupeOK() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        final List<User> users = this.groupe.getUsers();
        final User user = users.get(NUMBER_USERS);

        final List<String> usersName = new ArrayList<>();
        usersName.add(user.getUserName());

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        final GroupeDTO groupeDTO = this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        final Groupe groupe = this.groupeRepository.findByGroupeName(removeUsersGroupeParameter.getGroupeName()).get();

        Assertions.assertEquals(NUMBER_USERS, groupe.getUsers().size());

        Assertions.assertEquals(groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());

    }

    /**
     * Test to remove two users from a groupe
     */
    @Test
    public void testRemoveMultipleUserGroupeOK() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        final List<User> users = this.groupe.getUsers();

        final User userOne = users.get(NUMBER_USERS);
        final User userTwo = users.get(NUMBER_USERS - 1);

        final List<String> usersName = new ArrayList<>();
        usersName.add(userOne.getUserName());
        usersName.add(userTwo.getUserName());

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        final GroupeDTO groupeDTO = this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        final Groupe groupe = this.groupeRepository.findByGroupeName(removeUsersGroupeParameter.getGroupeName()).get();

        Assertions.assertEquals(NUMBER_USERS - 1, groupe.getUsers().size());

        Assertions.assertEquals(groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());
    }

    /**
     * Test with a group that doesn't exist
     */
    @Test
    public void testGroupeNotExist() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.faker.company().name());

        Assertions.assertThrows(GroupeNotExistException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    /**
     * Test with a group who the user are not the admin
     */
    @Test
    public void testNotAdminGroupe() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotAdminGroupeException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, user.getUserName()));

    }

    /**
     * Test to remove a user that doesn't exist
     */
    @Test
    public void testUserNotExist() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        final List<String> usersName = new ArrayList<>();
        usersName.add(this.faker.name().username());

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    /**
     * Test to remove a user who are not in the groupe
     */
    @Test
    public void testUserNotInGroupe() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        final List<String> usersName = new ArrayList<>();
        final User user = this.userRepository.save(this.factory.getUser());
        usersName.add(user.getUserName());

        final RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotInGroupeException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));
            
    }
    
}
