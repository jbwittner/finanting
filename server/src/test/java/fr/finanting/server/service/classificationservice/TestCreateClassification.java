package fr.finanting.server.service.classificationservice;

import fr.finanting.server.codegen.model.ClassificationParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    private ClassificationParameter classificationParameter;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.classificationServiceImpl = new ClassificationServiceImpl(this.classificationRepository,
                                                                        this.groupRepository,
                                                                        this.userRepository);

        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

        this.classificationParameter = new ClassificationParameter();
        this.classificationParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5).toLowerCase());
        this.classificationParameter.setDescription(this.faker.superhero().descriptor());
        this.classificationParameter.setLabel(this.faker.company().name());
    }

    @Test
    public void testCreateUserClassification() throws GroupNotExistException, UserNotInGroupException {

        this.classificationServiceImpl.createClassification(this.classificationParameter, this.user.getUserName());
        
        final Classification classification = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(this.classificationParameter.getAbbreviation().toUpperCase(), classification.getAbbreviation());
        Assertions.assertEquals(this.classificationParameter.getDescription(), classification.getDescritpion());
        Assertions.assertEquals(this.classificationParameter.getLabel(), classification.getLabel());
        Assertions.assertEquals(this.user.getUserName(), classification.getUser().getUserName());
        Assertions.assertNull(classification.getGroup());
        
    }

    @Test
    public void testCreateGroupClassification() throws GroupNotExistException, UserNotInGroupException {

        this.classificationParameter.setGroupName(this.group.getGroupName());

        this.classificationServiceImpl.createClassification(this.classificationParameter, this.user.getUserName());
        
        final Classification classification = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(this.classificationParameter.getAbbreviation().toUpperCase(), classification.getAbbreviation());
        Assertions.assertEquals(this.classificationParameter.getDescription(), classification.getDescritpion());
        Assertions.assertEquals(this.classificationParameter.getLabel(), classification.getLabel());
        Assertions.assertEquals(this.group.getGroupName(), classification.getGroup().getGroupName());
        Assertions.assertNull(classification.getUser());

    }

    @Test
    public void testCreateGroupClassificationWithGroupNotExist() throws GroupNotExistException, UserNotInGroupException {

        this.classificationParameter.setGroupName(this.faker.company().name());

        Assertions.assertThrows(GroupNotExistException.class,
            () -> this.classificationServiceImpl.createClassification(this.classificationParameter, this.user.getUserName()));

    }

    @Test
    public void testCreateGroupClassificationWithUserNotInGroup() throws GroupNotExistException, UserNotInGroupException {

        final Group group = this.testFactory.getGroup();

        this.classificationParameter.setGroupName(group.getGroupName());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.createClassification(this.classificationParameter, this.user.getUserName()));

    }
    
}
