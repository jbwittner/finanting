package fr.finanting.server.service.classificationservice;

import fr.finanting.server.codegen.model.UpdateClassificationParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ClassificationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestUpdateClassification extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private ClassificationServiceImpl classificationServiceImpl;

    private User user;
    private Group group;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.classificationServiceImpl = new ClassificationServiceImpl(this.classificationRepository,
                                                                                this.groupRepository,
                                                                                this.userRepository);
                                                                            
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);
                                                                        
    }

    @Test
    public void testUpdateUserClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.testFactory.getClassification(this.user);

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5).toLowerCase());
        updateClassificationParameter.setDescription(this.faker.superhero().descriptor());
        updateClassificationParameter.setLabel(this.faker.company().name());

        this.classificationServiceImpl.updateClassification(classification.getId(), updateClassificationParameter, this.user.getUserName());

        final Classification classificationSaved = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(updateClassificationParameter.getAbbreviation().toUpperCase(), classificationSaved.getAbbreviation());
        Assertions.assertEquals(updateClassificationParameter.getDescription(), classificationSaved.getDescritpion());
        Assertions.assertEquals(updateClassificationParameter.getLabel(), classificationSaved.getLabel());
        Assertions.assertEquals(this.user.getUserName(), classificationSaved.getUser().getUserName());
        Assertions.assertNull(classificationSaved.getGroup());

    }

    @Test
    public void testUpdateGroupClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.testFactory.getClassification(this.group);

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setAbbreviation(this.testFactory.getRandomAlphanumericString(5).toLowerCase());
        updateClassificationParameter.setDescription(this.faker.superhero().descriptor());
        updateClassificationParameter.setLabel(this.faker.company().name());

        this.classificationServiceImpl.updateClassification(classification.getId(), updateClassificationParameter, this.user.getUserName());

        final Classification classificationSaved = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(updateClassificationParameter.getAbbreviation().toUpperCase(), classificationSaved.getAbbreviation());
        Assertions.assertEquals(updateClassificationParameter.getDescription(), classificationSaved.getDescritpion());
        Assertions.assertEquals(updateClassificationParameter.getLabel(), classificationSaved.getLabel());
        Assertions.assertEquals(this.group.getGroupName(), classificationSaved.getGroup().getGroupName());
        Assertions.assertNull(classificationSaved.getUser());

    }

    @Test
    public void testUpdateClassificationNotExist() {
        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();

        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.classificationServiceImpl.updateClassification(this.testFactory.getRandomInteger(), updateClassificationParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateClassificationUserNotInGroup() {
        final Classification classification = this.testFactory.getClassification(this.group);

        final User otherUser = this.testFactory.getUser();

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.updateClassification(classification.getId(), updateClassificationParameter, otherUser.getUserName()));
    }

    @Test
    public void testUpdateNoUserClassificationNotExist() {
        final Classification classification = this.testFactory.getClassification(this.user);

        final User otherUser = this.testFactory.getUser();

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();

        Assertions.assertThrows(ClassificationNoUserException.class,
            () -> this.classificationServiceImpl.updateClassification(classification.getId(), updateClassificationParameter, otherUser.getUserName()));
    }


    
}
