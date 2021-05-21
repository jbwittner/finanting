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

    private Group groupOne;
    private Group groupTwo;

    @Override
    protected void initDataBeforeEach() {
        this.userOne = this.testFactory.getUser();
        this.userTwo = this.testFactory.getUser();
        this.groupOne = this.testFactory.getGroup(userOne);
        this.groupTwo = this.testFactory.getGroup(userTwo);
    }

    @Test
    public void testUserCheckIfCanAssociatedOk() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setUser(this.userOne);
        
        motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo);
    }

    @Test
    public void testGroupCheckIfCanAssociatedOk() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupOne);
        motherGroupUserElementTwo.setGroup(this.groupOne);
        
        motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo);
    }

    @Test
    public void testUserCheckIfCanAssociatedWithGroup() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setGroup(this.groupOne);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testGroupCheckIfCanAssociatedWithUser() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupOne);
        motherGroupUserElementTwo.setUser(this.userOne);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testUserCheckIfCanAssociatedWithAnotherUser() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setUser(this.userOne);
        motherGroupUserElementTwo.setUser(this.userTwo);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }

    @Test
    public void testGroupCheckIfCanAssociatedWithAnotherGroup() throws BadAssociationElementException{
        final MotherGroupUserElement motherGroupUserElementOne = new MotherGroupUserElement();
        final MotherGroupUserElement motherGroupUserElementTwo = new MotherGroupUserElement();
        motherGroupUserElementOne.setGroup(this.groupOne);
        motherGroupUserElementTwo.setGroup(this.groupTwo);
        
        Assertions.assertThrows(BadAssociationElementException.class,
            () -> motherGroupUserElementOne.checkIfCanAssociated(motherGroupUserElementTwo));

    }
    
    
}
