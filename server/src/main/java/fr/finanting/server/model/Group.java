package fr.finanting.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.exception.UserNotInGroupException;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "USER_GROUPS")
@Data
public class Group extends MotherPersistant {

	@Column(name = "GROUP_NAME", nullable = false, unique = true)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ADMIN", nullable = false)
    private User userAdmin;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<BankingAccount> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "USERS_GROUPS_ASSOCIATIONS",
                joinColumns = { 
                    @JoinColumn(name = "GROUP_ID_JOIN") }, inverseJoinColumns = { 
                    @JoinColumn(name = "USER_ID_INVERSE") })
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Group [id=" + this.id + ", groupName=" + groupName + ", userAdmin=" + userAdmin + "]";
    }

    public void checkAreInGroup(final User user) throws UserNotInGroupException{
        boolean areInGroup = false;

        for(final User userInGroup : this.users){
            if(user.getUserName().equals(userInGroup.getUserName())){
                areInGroup = true;
                break;
            }
        }

        if(!areInGroup){
            throw new UserNotInGroupException(user, this);
        }
    }
    
}
