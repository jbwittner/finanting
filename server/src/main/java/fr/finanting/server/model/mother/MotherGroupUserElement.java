package fr.finanting.server.model.mother;

import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import fr.finanting.server.exception.BadAssociationElementException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.model.Group;
import fr.finanting.server.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper=true)
public class MotherGroupUserElement extends MotherPersistant {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    protected Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    public void checkIfUsable(User user) throws BadAssociationElementException, UserNotInGroupException{
        if(this.group == null){
            if(!this.user.getId().equals(user.getId())){
                throw new BadAssociationElementException();
            }
        } else {
            this.group.checkAreInGroup(user);
        }
    }

    public void checkIfCanAssociated(MotherGroupUserElement otherMotherGroupUserElement) throws BadAssociationElementException{
        
        if(this.user != null && otherMotherGroupUserElement.getUser() != null){
            if(!this.user.getId().equals(otherMotherGroupUserElement.getUser().getId())){
                throw new BadAssociationElementException();
            }
        } else if(this.group != null && otherMotherGroupUserElement.getGroup() != null){
            if(!this.group.getId().equals(otherMotherGroupUserElement.getGroup().getId())){
                throw new BadAssociationElementException();
            }
        } else {
            throw new BadAssociationElementException();
        }
    }
    
}
