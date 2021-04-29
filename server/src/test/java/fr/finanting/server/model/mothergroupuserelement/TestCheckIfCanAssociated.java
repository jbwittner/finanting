package fr.finanting.server.model.mothergroupuserelement;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.mother.MotherGroupUserElement;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCheckIfCanAssociated extends AbstractMotherIntegrationTest {

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
    public void testUserCheckIfCanAssociatedOk() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.groupeOne.getUserAdmin());
        motherGroupUserElementTwo.setUser(this.groupeOne.getUserAdmin());
        
        motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo);
    }

    @Test
    public void testGroupCheckIfCanAssociatedOk() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupeOne);
        motherGroupUserElementTwo.setGroup(this.groupeOne);
        
        motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo);
    }

    @Test
    public void testUserCheckIfCanAssociatedWithGroup() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.groupeOne.getUserAdmin());
        motherGroupUserElementTwo.setGroup(this.groupeOne);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testGroupCheckIfCanAssociatedWithUser() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupeOne);
        motherGroupUserElementTwo.setUser(this.groupeOne.getUserAdmin());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testUserCheckIfCanAssociatedWithAnotherUser() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.groupeOne.getUserAdmin());
        motherGroupUserElementTwo.setUser(this.groupeTwo.getUserAdmin());
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testGroupCheckIfCanAssociatedWithAnotherGroup() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupeOne);
        motherGroupUserElementTwo.setGroup(this.groupeTwo);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }
    
    
}
