package fr.finanting.server.service.thirdservice;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.exception.ThirdNoUserException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.Third;
import fr.finanting.server.model.User;
import fr.finanting.server.parameter.DeleteThirdParameter;
import fr.finanting.server.repository.CategoryRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.ThirdRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ThirdServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestDeleteThird extends AbstractMotherIntegrationTest {

    @Autowired
    private ThirdRepository thirdRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ThirdServiceImpl thirdServiceImpl;

    private User user;
    private Group group;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.thirdServiceImpl = new ThirdServiceImpl(this.thirdRepository,
                                                    this.userRepository,
                                                    this.groupRepository,
                                                    this.categoryRepository);
        
        this.user = this.testFactory.getUser();
        this.group = this.testFactory.getGroup(user);

    }

    @Test
    public void testDeleteUserThird() throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{
        final Third third = this.testFactory.getThird(this.user);

        final Integer thirdId = third.getId();

        final DeleteThirdParameter deleteThirdParameter = new DeleteThirdParameter();
        deleteThirdParameter.setId(thirdId);

        this.thirdServiceImpl.deleteThird(deleteThirdParameter, this.user.getUserName());

        final Optional<Third> optionalThird = this.thirdRepository.findById(thirdId);

        Assertions.assertFalse(optionalThird.isPresent());
    }

    @Test
    public void testDeleteGroupThird() throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{
        final Third third = this.testFactory.getThird(this.group);

        final Integer thirdId = third.getId();

        final DeleteThirdParameter deleteThirdParameter = new DeleteThirdParameter();
        deleteThirdParameter.setId(thirdId);

        this.thirdServiceImpl.deleteThird(deleteThirdParameter, this.user.getUserName());

        final Optional<Third> optionalThird = this.thirdRepository.findById(thirdId);

        Assertions.assertFalse(optionalThird.isPresent());
    }

    @Test
    public void testDeleteOtherUserThird() throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{
        final User otherUser = this.testFactory.getUser();
        final Third third = this.testFactory.getThird(otherUser);

        final Integer thirdId = third.getId();

        final DeleteThirdParameter deleteThirdParameter = new DeleteThirdParameter();
        deleteThirdParameter.setId(thirdId);

        Assertions.assertThrows(ThirdNoUserException.class,
            () -> this.thirdServiceImpl.deleteThird(deleteThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testDeleteOtherGroupThird() throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{
        Group otherGroup = this.testFactory.getGroup();
        final Third third = this.testFactory.getThird(otherGroup);

        final Integer thirdId = third.getId();

        final DeleteThirdParameter deleteThirdParameter = new DeleteThirdParameter();
        deleteThirdParameter.setId(thirdId);

        Assertions.assertThrows(UserNotInGroupException.class,
            () -> this.thirdServiceImpl.deleteThird(deleteThirdParameter, this.user.getUserName()));
    }

    @Test
    public void testDeleteNotExistentThird() throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException{
        final DeleteThirdParameter deleteThirdParameter = new DeleteThirdParameter();
        deleteThirdParameter.setId(this.testFactory.getRandomInteger());

        Assertions.assertThrows(ThirdNotExistException.class,
            () -> this.thirdServiceImpl.deleteThird(deleteThirdParameter, this.user.getUserName()));
    }
    
}
