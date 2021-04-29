package fr.finanting.server.model.mothergroupuserelement;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.mother.MotherGroupUserElement;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCheckIfUsable extends AbstractMotherIntegrationTest {

    private Group groupeOne;
    private Group groupeTwo;

    private Group getGroup(Integer groupId){
        final Group group = new Group();
        group.setId(groupId);
        group.setGroupName(this.faker.company().name());
        final User user = this.factory.getUser();
        user.setId(groupId * 10);
        group.setUserAdmin(user);
        final List<User> users = new ArrayList<>();
        users.add(user);
        group.setUsers(users);
        return group;
    }

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.groupeOne = this.getGroup(1);
        this.groupeTwo = this.getGroup(2);
    }

    @Test
    public void testGroupOk() throws BadAssociationElementException, UserNotInGroupException{
        MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setGroup(this.groupeOne);
        motherGroupElement.checkIfUsable(this.groupeOne.getUserAdmin());
    }

    @Test
    public void testUserOk() throws BadAssociationElementException, UserNotInGroupException{
        MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setUser(this.groupeOne.getUserAdmin());
        motherGroupElement.checkIfUsable(this.groupeOne.getUserAdmin());
    }

    @Test
    public void testGroupWithOtherUser(){
        MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setGroup(this.groupeOne);
        Assertions.assertThrows(UserNotInGroupException.class, () -> motherGroupElement.checkIfUsable(this.groupeTwo.getUserAdmin()));
    }

    @Test
    public void testUserWithOtherUser(){
        MotherGroupUserElement motherGroupElement = new MotherGroupUserElement();
        motherGroupElement.setUser(this.groupeOne.getUserAdmin());
        Assertions.assertThrows(BadAssociationElementException.class, () -> motherGroupElement.checkIfUsable(this.groupeTwo.getUserAdmin()));
    }

}
