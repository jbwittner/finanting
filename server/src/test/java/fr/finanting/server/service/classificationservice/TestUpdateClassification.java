package fr.finanting.server.service.classificationservice;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Classification;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.UpdateClassificationParameter;
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
                                                                            
        this.group = this.factory.getGroup();
        this.userRepository.save(this.group.getUserAdmin());

        this.user = this.userRepository.save(this.factory.getUser());

        final List<User> users = this.group.getUsers();
        users.add(user);
        this.group.setUsers(users);

        this.group = this.groupRepository.save(group);

    }

    @Test
    public void testUpdateUserClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.classificationRepository.save(this.factory.getClassification(this.user));

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5).toLowerCase());
        updateClassificationParameter.setDescritpion(this.faker.superhero().descriptor());
        updateClassificationParameter.setLabel(this.faker.company().name());
        updateClassificationParameter.setId(classification.getId());

        this.classificationServiceImpl.updateClassification(updateClassificationParameter, this.user.getUserName());

        final Classification classificationSaved = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(updateClassificationParameter.getAbbreviation().toUpperCase(), classificationSaved.getAbbreviation());
        Assertions.assertEquals(updateClassificationParameter.getDescritpion(), classificationSaved.getDescritpion());
        Assertions.assertEquals(updateClassificationParameter.getLabel(), classificationSaved.getLabel());
        Assertions.assertEquals(this.user.getUserName(), classificationSaved.getUser().getUserName());
        Assertions.assertNull(classificationSaved.getGroup());

    }

    @Test
    public void testUpdateGroupClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.classificationRepository.save(this.factory.getClassification(this.group));

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setAbbreviation(this.factory.getRandomAlphanumericString(5).toLowerCase());
        updateClassificationParameter.setDescritpion(this.faker.superhero().descriptor());
        updateClassificationParameter.setLabel(this.faker.company().name());
        updateClassificationParameter.setId(classification.getId());

        this.classificationServiceImpl.updateClassification(updateClassificationParameter, this.user.getUserName());

        final Classification classificationSaved = this.classificationRepository.findAll().get(0);

        Assertions.assertEquals(updateClassificationParameter.getAbbreviation().toUpperCase(), classificationSaved.getAbbreviation());
        Assertions.assertEquals(updateClassificationParameter.getDescritpion(), classificationSaved.getDescritpion());
        Assertions.assertEquals(updateClassificationParameter.getLabel(), classificationSaved.getLabel());
        Assertions.assertEquals(this.group.getGroupName(), classificationSaved.getGroup().getGroupName());
        Assertions.assertNull(classificationSaved.getUser());

    }

    @Test
    public void testUpdateClassificationNotExist() {
        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setId(this.factory.getRandomInteger());

        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.classificationServiceImpl.updateClassification(updateClassificationParameter, this.user.getUserName()));
    }

    @Test
    public void testUpdateClassificationUserNotInGroup() {
        final Classification classification = this.classificationRepository.save(this.factory.getClassification(this.group));

        final User otherUser = this.userRepository.save(this.factory.getUser());

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setId(classification.getId());

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.updateClassification(updateClassificationParameter, otherUser.getUserName()));
    }

    @Test
    public void testUpdateNoUserClassificationNotExist() {
        final Classification classification = this.classificationRepository.save(this.factory.getClassification(this.user));

        final User otherUser = this.userRepository.save(this.factory.getUser());

        final UpdateClassificationParameter updateClassificationParameter = new UpdateClassificationParameter();
        updateClassificationParameter.setId(classification.getId());

        Assertions.assertThrows(ClassificationNoUserException.class,
            () -> this.classificationServiceImpl.updateClassification(updateClassificationParameter, otherUser.getUserName()));
    }


    
}
