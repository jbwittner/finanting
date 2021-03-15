package fr.finanting.server.service.groupeservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.GroupeDTO;
import fr.finanting.server.exception.GroupeNameAlreadyExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.GroupeCreationParameter;
import fr.finanting.server.repository.GroupeRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.GroupeServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateGroupe extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupeRepository groupeRepository;

    private GroupeServiceImpl groupeServiceImpl;

    private User userPrincipal;
    private Integer NUMBER_ACCOUNT = 10;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupeServiceImpl = new GroupeServiceImpl(this.userRepository, this.groupeRepository);
        this.userPrincipal = this.userRepository.save(factory.getUser());
    }

    @Test
    public void testCreateGroupeWithoutUsers() throws GroupeNameAlreadyExistException, UserNotExistException {
        final GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(this.faker.company().name());
        groupeCreationParameter.setUsersName(new ArrayList<>());

        this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName());

        final Groupe groupe = this.groupeRepository.findByGroupeName(groupeCreationParameter.getGroupeName()).get();

        Assertions.assertEquals(groupeCreationParameter.getGroupeName(), groupe.getGroupeName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUserAdmin().getUserName());
        Assertions.assertEquals(1, groupe.getUsers().size());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUsers().get(0).getUserName());
    }

    @Test
    public void testCreateGroupeWithUsers() throws GroupeNameAlreadyExistException, UserNotExistException {
        final GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(this.faker.company().name());

        final List<String> usersNameList = new ArrayList<>(); 

        for(Integer index = 0; index < NUMBER_ACCOUNT; index ++){
            final User user = this.userRepository.save(factory.getUser());
            usersNameList.add(user.getUserName());
        }

        groupeCreationParameter.setUsersName(usersNameList);

        final GroupeDTO groupeDTO = this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName());

        final Groupe groupe = this.groupeRepository.findByGroupeName(groupeCreationParameter.getGroupeName()).get();

        Assertions.assertEquals(groupeCreationParameter.getGroupeName(), groupe.getGroupeName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUserAdmin().getUserName());
        Assertions.assertEquals(NUMBER_ACCOUNT + 1, groupe.getUsers().size());

        for(final String userNames : usersNameList){
            boolean userAdded = false;

            for(final User usersGroupe : groupe.getUsers()){

                if(userNames.equals(usersGroupe.getUserName())){
                    userAdded = true;
                }
    
            }

            Assertions.assertTrue(userAdded);

        }

        Assertions.assertEquals(groupe.getGroupeName(), groupeDTO.getGroupeName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getUserAdmin().getUserName());
        Assertions.assertEquals(groupe.getUserAdmin().getUserName(), groupeDTO.getGroupeUsers().get(0).getUserName());

    }

    @Test
    public void testGroupeNameAlreadyUsed() throws GroupeNameAlreadyExistException, UserNotExistException{
        final Groupe groupe = this.factory.getGroupe();
        this.userRepository.save(groupe.getUserAdmin());
        this.groupeRepository.save(groupe);

        final GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(groupe.getGroupeName());

        Assertions.assertThrows(GroupeNameAlreadyExistException.class,
            () -> this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName()));
    }

    @Test
    public void testUserNotExist() throws GroupeNameAlreadyExistException, UserNotExistException {
        final GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(this.faker.company().name());

        final List<String> usersNameList = new ArrayList<>();
        usersNameList.add(this.faker.name().username());

        groupeCreationParameter.setUsersName(usersNameList);

        Assertions.assertThrows(UserNotExistException.class,
            () -> this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName()));
    }
    
}
