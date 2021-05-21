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
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ClassificationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestDeleteClassification extends AbstractMotherIntegrationTest {

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
    protected void initDataBeforeEach() {
        this.classificationServiceImpl = new ClassificationServiceImpl(this.classificationRepository,
                                                                                this.groupRepository,
                                                                                this.userRepository);
                                                                            
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

    }

    @Test
    public void testDeleteUserClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.testFactory.getClassification(this.user);

        this.classificationServiceImpl.deleteClassification(classification.getId(), this.user.getUserName());

        final List<Classification> classifications = this.classificationRepository.findAll();

        Assertions.assertEquals(0, classifications.size());

    }

    @Test
    public void testDeleteGroupClassification() throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException {
        final Classification classification = this.testFactory.getClassification(this.group);

        this.classificationServiceImpl.deleteClassification(classification.getId(), this.user.getUserName());

        final List<Classification> classifications = this.classificationRepository.findAll();

        Assertions.assertEquals(0, classifications.size());

    }

    @Test
    public void testDeleteClassificationNotExist() {
        Assertions.assertThrows(ClassificationNotExistException.class,
            () -> this.classificationServiceImpl.deleteClassification(this.testFactory.getRandomInteger(), this.user.getUserName()));
    }

    @Test
    public void testDeleteClassificationUserNotInGroup() {
        final Classification classification = this.testFactory.getClassification(this.group);

        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.classificationServiceImpl.deleteClassification(classification.getId(), otherUser.getUserName()));
    }

    @Test
    public void testDeleteNoUserClassificationNotExist() {
        final Classification classification = this.testFactory.getClassification(this.user);

        final User otherUser = this.testFactory.getUser();

        Assertions.assertThrows(ClassificationNoUserException.class,
            () -> this.classificationServiceImpl.deleteClassification(classification.getId(), otherUser.getUserName()));
    }

    
}
