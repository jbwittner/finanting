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
import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.AddUsersGroupeParameter;
import fr.finanting.server.repository.GroupeRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupeServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

/**
 * Test class to test addUsersGroupe method
 */
public class TestAddUsersGroupe extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    private GroupeServiceImpl groupeServiceImpl;
    private Groupe groupe;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupeServiceImpl = new GroupeServiceImpl(this.userRepository, this.groupeRepository);
        this.groupe = this.factory.getGroupe();
        this.userRepository.save(this.groupe.getUserAdmin());
        this.groupeRepository.save(this.groupe);
    }

    /**
     * Test to add a user in a group
     */
    @Test
    public void testAddUserOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        final List<String> userList = new ArrayList<>();
        final User user = this.userRepository.save(this.factory.getUser());
        userList.add(user.getUserName());
        addUsersGroupeParameter.setUsersName(userList);
        
        final GroupeDTO groupeDTO = this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Assertions.assertEquals(2, this.groupe.getUsers().size());

        final List<User> users = this.groupe.getUsers();

        boolean isInGroupe = false;

        for(final User userInGroupe : users){
            if(userInGroupe.getUserName().equals(user.getUserName())){
                isInGroupe = true;
                break;
            }
        }

        Assertions.assertTrue(isInGroupe);

        Assertions.assertEquals(this.groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(this.groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(this.groupe.getUserAdmin().getUserName(), groupeDTO.getGroupeUsers().get(0).getUserName());
        Assertions.assertEquals(user.getUserName(), groupeDTO.getGroupeUsers().get(1).getUserName());

    }

    /**
     * Test to add a user who are already in the group
     */
    @Test
    public void testAddUserAlreadyInGroupeOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        final List<String> userList = new ArrayList<>();
        userList.add(this.groupe.getUserAdmin().getUserName());
        addUsersGroupeParameter.setUsersName(userList);
        
        final GroupeDTO groupeDTO = this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Assertions.assertEquals(1, this.groupe.getUsers().size());

        Assertions.assertEquals(this.groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(this.groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(this.groupe.getUserAdmin().getUserName(), groupeDTO.getGroupeUsers().get(0).getUserName());

    }

    /**
     * Test to add a user with a non admin user
     */
    @Test
    public void testNotAdminExeptionOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());

        final User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotAdminGroupeException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, user.getUserName()));

    }

    /**
     * Test to add a user to a group that doesn't exist
     */
    @Test
    public void testGroupeNoeExistOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.faker.company().name());

        Assertions.assertThrows(GroupeNotExistException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    /**
     * Test to add a user who doesn't exist
     */
    @Test
    public void testUserNotExistExceptionOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        final List<String> userList = new ArrayList<>();
        userList.add(this.faker.name().username());
        addUsersGroupeParameter.setUsersName(userList);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }
    
}
