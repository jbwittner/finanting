package fr.finanting.server.service.classificationservice;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.CreateClassificationParameter;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ClassificationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCreateClassification extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private ClassificationServiceImpl classificationServiceImpl;

    private User user;
    private Group group;
    private CreateClassificationParameter createClassificationParameter;

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

        this.createClassificationParameter = new CreateClassificationParameter();
        this.createClassificationParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5).toLowerCase());
        this.createClassificationParameter.setDescritpion(this.faker.superhero().descriptor());
        this.createClassificationParameter.setLabel(this.faker.company().name());
    }

    @Test
    public void testCreateUserClassification() throws GroupNotExistException, UserNotInGroupException {

        this.classificationServiceImpl.createClassification(this.createClassificationParameter, this.user.getUserName());
        
        final Classification classification = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(this.createClassificationParameter.getAbbreviation().toUpperCase(), classification.getAbbreviation());
        Assertions.assertEquals(this.createClassificationParameter.getDescritpion(), classification.getDescritpion());
        Assertions.assertEquals(this.createClassificationParameter.getLabel(), classification.getLabel());
        Assertions.assertEquals(this.user.getUserName(), classification.getUser().getUserName());
        Assertions.assertNull(classification.getGroup());
        
    }

    @Test
    public void testCreateGroupClassification() throws GroupNotExistException, UserNotInGroupException {

        this.createClassificationParameter.setGroupName(this.group.getGroupName());

        this.classificationServiceImpl.createClassification(this.createClassificationParameter, this.user.getUserName());
        
        final Classification classification = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(this.createClassificationParameter.getAbbreviation().toUpperCase(), classification.getAbbreviation());
        Assertions.assertEquals(this.createClassificationParameter.getDescritpion(), classification.getDescritpion());
        Assertions.assertEquals(this.createClassificationParameter.getLabel(), classification.getLabel());
        Assertions.assertEquals(this.group.getGroupName(), classification.getGroup().getGroupName());
        Assertions.assertNull(classification.getUser());

    }

    @Test
    public void testCreateGroupClassificationWithGroupNotExist() throws GroupNotExistException, UserNotInGroupException {

        this.createClassificationParameter.setGroupName(this.faker.company().name());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.classificationServiceImpl.createClassification(this.createClassificationParameter, this.user.getUserName()));

    }

    @Test
    public void testCreateGroupClassificationWithUserNotInGroup() throws GroupNotExistException, UserNotInGroupException {

        Group group = this.factory.getGroup();
        this.userRepository.save(group.getUserAdmin());
        group = this.groupRepository.save(group);

        this.createClassificationParameter.setGroupName(group.getGroupName());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.createClassification(this.createClassificationParameter, this.user.getUserName()));

    }
    
}
