package fr.finanting.server.service.classificationservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.dto.ClassificationDTO;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ClassificationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestGetGroupClassifications extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private ClassificationServiceImpl classificationServiceImpl;

    private User user;
    private Group group;

    private int NUMBER_CLASSIFICATIONS = 4;


    @Override
    protected void initDataBeforeEach() throws Exception {
        this.classificationServiceImpl = new ClassificationServiceImpl(this.classificationRepository,
                                                                                this.groupRepository,
                                                                                this.userRepository);

        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);
    }

    @Test
    public void testGetGroupClassifications() throws GroupNotExistException, UserNotInGroupException{

        List<Classification> classifications = new ArrayList<>();

        for(int index = 0; index < NUMBER_CLASSIFICATIONS; index ++){

            Classification classification = this.classificationRepository.save(this.factory.getClassification(this.group));
            classifications.add(classification);
            
        }

        List<ClassificationDTO> classificationDTOs = this.classificationServiceImpl.getGroupClassifications(this.group.getGroupName(), this.user.getUserName());

        Assertions.assertEquals(NUMBER_CLASSIFICATIONS, classificationDTOs.size());

        for(ClassificationDTO classificationDTO : classificationDTOs){
            boolean isPresent = false;

            for(Classification classification : classifications){
                if(classification.getId().equals(classificationDTO.getId())){
                    isPresent = true;
                    Assertions.assertEquals(classificationDTO.getAbbreviation(), classification.getAbbreviation());
                    Assertions.assertEquals(classificationDTO.getDescritpion(), classification.getDescritpion());
                    Assertions.assertEquals(classificationDTO.getLabel(), classification.getLabel());
                }
            }

            Assertions.assertTrue(isPresent);
        }

    }

    @Test
    public void testGetGroupClassificationsWithNonExistentGroup(){

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.classificationServiceImpl.getGroupClassifications(this.faker.company().name(), this.user.getUserName()));

    }

    @Test
    public void testGetGroupClassificationsWithUserNotInGroup(){

        User otherUser = this.userRepository.save(this.factory.getUser());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.getGroupClassifications(this.group.getGroupName(), otherUser.getUserName()));

    }
    
}