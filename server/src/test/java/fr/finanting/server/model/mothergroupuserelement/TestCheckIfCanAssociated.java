package fr.finanting.server.model.mothergroupuserelement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import fr.finanting.server.model.mother.MotherGroupUserElement;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestCheckIfCanAssociated extends AbstractMotherIntegrationTest {

    private User userOne;
    private User userTwo;

    private Group groupeOne;
    private Group groupeTwo;

    @Override
    protected void initDataBeforeEach() throws Exception {
        this.userOne = this.testFactory.getUser();
        this.userTwo = this.testFactory.getUser();
        this.groupeOne = this.testFactory.getGroup(userOne);
        this.groupeTwo = this.testFactory.getGroup(userTwo);
    }

    @Test
    public void testUserCheckIfCanAssociatedOk() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setUser(this.userOne);
        
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
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setGroup(this.groupeOne);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testGroupCheckIfCanAssociatedWithUser() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupeOne);
        motherGroupUserElementTwo.setUser(this.userOne);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testUserCheckIfCanAssociatedWithAnotherUser() throws BadAssociationElementException{
        MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setUser(this.userTwo);
        
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
