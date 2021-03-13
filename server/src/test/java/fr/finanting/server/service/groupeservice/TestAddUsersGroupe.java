package fr.finanting.server.service.groupeservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void addUserOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        List<String> userList = new ArrayList<>();
        User user = this.userRepository.save(this.factory.getUser());
        userList.add(user.getUserName());
        addUsersGroupeParameter.setUsersName(userList);
        
        this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Assertions.assertEquals(2, this.groupe.getUsers().size());

        List<User> users = this.groupe.getUsers();

        boolean isInGroupe = false;

        for(User userInGroupe : users){
            if(userInGroupe.getUserName().equals(user.getUserName())){
                isInGroupe = true;
                break;
            }
        }

        Assertions.assertTrue(isInGroupe);

    }

    @Test
    public void addUserAlreadyInGroupeOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        List<String> userList = new ArrayList<>();
        userList.add(this.groupe.getUserAdmin().getUserName());
        addUsersGroupeParameter.setUsersName(userList);
        
        this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName());

        Assertions.assertEquals(1, this.groupe.getUsers().size());

        List<User> users = this.groupe.getUsers();

        boolean isInGroupe = false;

        for(User userInGroupe : users){
            if(userInGroupe.getUserName().equals(this.groupe.getUserAdmin().getUserName())){
                isInGroupe = true;
                break;
            }
        }

        Assertions.assertTrue(isInGroupe);

    }

    @Test
    public void notAdminExeptionOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());

        User user = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(NotAdminGroupeException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, user.getUserName()));

    }

    @Test
    public void groupeNoeExistOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.faker.company().name());

        Assertions.assertThrows(GroupeNotExistException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }

    @Test
    public void userNotExistExceptionOk() throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        AddUsersGroupeParameter addUsersGroupeParameter = new AddUsersGroupeParameter();
        addUsersGroupeParameter.setGroupeName(this.groupe.getGroupeName());
        List<String> userList = new ArrayList<>();
        userList.add(this.faker.name().username());
        addUsersGroupeParameter.setUsersName(userList);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupeServiceImpl.addUsersGroupe(addUsersGroupeParameter, this.groupe.getUserAdmin().getUserName()));

    }
    
}
