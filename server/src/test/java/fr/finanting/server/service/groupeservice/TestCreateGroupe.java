package fr.finanting.server.service.groupeservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.GroupeNameAlreadyExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.model.Groupe;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.GroupeCreationParameter;
import fr.finanting.server.repository.GroupeRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.GroupeService;
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
        groupeServiceImpl = new GroupeServiceImpl(this.userRepository, this.groupeRepository);
        this.userPrincipal = this.userRepository.save(factory.getUser());
    }

    @Test
    public void createGroupeWithoutUsers() throws GroupeNameAlreadyExistException, UserNotExistException {
        GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(this.faker.company().name());
        groupeCreationParameter.setUsersName(new ArrayList<>());

        this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName());

        Groupe groupe = this.groupeRepository.findByGroupeName(groupeCreationParameter.getGroupeName()).get();

        Assertions.assertEquals(groupeCreationParameter.getGroupeName(), groupe.getGroupeName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUserAdmin().getUserName());
        Assertions.assertEquals(1, groupe.getUsers().size());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUsers().get(0).getUserName());
    }

    @Test
    public void createGroupeWithUsers() throws GroupeNameAlreadyExistException, UserNotExistException {
        GroupeCreationParameter groupeCreationParameter = new GroupeCreationParameter();
        groupeCreationParameter.setGroupeName(this.faker.company().name());

        List<String> usersNameList = new ArrayList<>(); 

        for(Integer index = 0; index < NUMBER_ACCOUNT; index ++){
            User user = this.userRepository.save(factory.getUser());
            usersNameList.add(user.getUserName());
        }

        groupeCreationParameter.setUsersName(usersNameList);

        this.groupeServiceImpl.createGroupe(groupeCreationParameter, this.userPrincipal.getUserName());

        Groupe groupe = this.groupeRepository.findByGroupeName(groupeCreationParameter.getGroupeName()).get();

        Assertions.assertEquals(groupeCreationParameter.getGroupeName(), groupe.getGroupeName());
        Assertions.assertEquals(this.userPrincipal.getUserName(), groupe.getUserAdmin().getUserName());
        Assertions.assertEquals(NUMBER_ACCOUNT + 1, groupe.getUsers().size());

        for(String userNames : usersNameList){
            boolean userAdded = false;

            for(User usersGroupe : groupe.getUsers()){

                if(userNames.equals(usersGroupe.getUserName())){
                    userAdded = true;
                }
    
            }

            Assertions.assertTrue(userAdded);

        }

    }

    @Test
    public void groupeNameAlreadyUsed(){
        Groupe groupe = this.factory.getGroupe();
        this.userRepository.save(groupe.getUserAdmin());
        this.groupeRepository.save(groupe);
    }
    
}
