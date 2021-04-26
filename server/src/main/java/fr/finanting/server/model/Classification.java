package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.UserNotInGroupException;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CLASSIFICATIONS")
@Data
public class Classification extends MotherPersistant {

    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "ABBREVIATION", nullable = false, length = 6)
    private String abbreviation;

    @Column(name = "DESCRIPTION")
    private String descritpion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public void checkIfUsable(User user) throws ClassificationNoUserException, UserNotInGroupException{
        if(this.group == null){
            if(!this.user.getUserName().equals(user.getUserName())){
                throw new ClassificationNoUserException(this.id);
            }
        } else {
            this.group.checkAreInGroup(user);
        }
    }
    
}
