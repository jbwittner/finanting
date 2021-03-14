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
        List<User> users = this.groupe.getUsers();
        for(Integer index = 0; index < NUMBER_USERS; index ++){
            User user = this.userRepository.save(this.factory.getUser());
            users.add(user);
        }
        this.groupeRepository.save(this.groupe);
    }

    @Test
    public void removeOneUserGroupeOK() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        List<User> users = this.groupe.getUsers();
        User user = users.get(NUMBER_USERS);

        List<String> usersName = new ArrayList<>();
        usersName.add(user.getUserName());

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        GroupeDTO groupeDTO = this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Groupe groupe = this.groupeRepository.findByGroupeName(removeUsersGroupeParameter.getGroupeName()).get();

        Assertions.assertEquals(NUMBER_USERS, groupe.getUsers().size());

        Assertions.assertEquals(groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());

    }

    @Test
    public void removeMultipleUserGroupeOK() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        List<User> users = this.groupe.getUsers();

        User userOne = users.get(NUMBER_USERS);
        User userTwo = users.get(NUMBER_USERS - 1);

        List<String> usersName = new ArrayList<>();
        usersName.add(userOne.getUserName());
        usersName.add(userTwo.getUserName());

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        GroupeDTO groupeDTO = this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Groupe groupe = this.groupeRepository.findByGroupeName(removeUsersGroupeParameter.getGroupeName()).get();

        Assertions.assertEquals(NUMBER_USERS - 1, groupe.getUsers().size());

        Assertions.assertEquals(groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());
    }

    @Test
    public void groupeNotExist() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.faker.company().name());

        Assertions.assertThrows(GroupeNotExistException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    @Test
    public void notAdminGroupe() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());

        User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotAdminGroupeException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, user.getUserName()));

    }

    @Test
    public void userNotExist() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        List<String> usersName = new ArrayList<>();
        usersName.add(this.faker.name().username());

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    @Test
    public void userNotInGroupe() throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        List<String> usersName = new ArrayList<>();
        User user = this.userRepository.save(this.factory.getUser());
        usersName.add(user.getUserName());

        RemoveUsersGroupeParameter removeUsersGroupeParameter = new RemoveUsersGroupeParameter();
        removeUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        removeUsersGroupeParameter.setUsersName(usersName);

        Assertions.assertThrows(UserNotInGroupeException.class,
            () -> this.groupeServiceImpl.removeUsersGroupe(removeUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));
            
    }


    
}
