package fr.finanting.server.model.mothergroupuserelement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.exception.NotUserElementException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.mother.MotherGroupUserElement;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCheckIfUsable extends AbstractMotherIntegrationTest {

    private User userOne;
    private User userTwo;

    private Group groupOne;

    @Override
    protected void initDataBeforeEach() {
        this.userOne = this.testFactory.getUser();
        this.userTwo = this.testFactory.getUser();
        this.groupOne = this.testFactory.getGroup(userOne);
    }

    @Test
    public void testGroupOk() throws NotUserElementException, UserNotInGroupException{
        final MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setGroup(this.groupOne);
        motherGroupElement.checkIfUsable(this.userOne);
    }

    @Test
    public void testUserOk() throws NotUserElementException, UserNotInGroupException{
        final MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setUser(this.userOne);
        motherGroupElement.checkIfUsable(this.userOne);
    }

    @Test
    public void testGroupWithOtherUser(){
        final MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setGroup(this.groupOne);
        Assertions.assertThrows(UserNotInGroupException.class, () -> motherGroupElement.checkIfUsable(this.userTwo));
    }

    @Test
    public void testUserWithOtherUser(){
        final MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setUser(this.userOne);
        Assertions.assertThrows(NotUserElementException.class, () -> motherGroupElement.checkIfUsable(this.userTwo));
    }

}
