package fr.finanting.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.exception.CategoryNoUserException;
import fr.finanting.server.exception.UserNotInGroupException;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CATEGORIES")
@Data
public class Category extends MotherPersistant {

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "ABBREVIATION", nullable = false, length = 6)
    private String abbreviation;

    @Column(name = "DESCRIPTION")
    private String descritpion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLES")
    private CategoryType categoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Override
    public String toString() {
        return "Category [id= " + this.id + ", abbreviation=" + abbreviation + ", label=" + label + ", user=" + user + "]";
    }

    public void checkIfUsable(User user) throws CategoryNoUserException, UserNotInGroupException{
        if(this.group == null){
            if(!this.user.getUserName().equals(user.getUserName())){
                throw new CategoryNoUserException(this.id);
            }
        } else {
            this.group.checkAreInGroup(user);
        }
    }
    
}
