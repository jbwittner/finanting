package fr.finanting.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.model.mother.MotherPersistent;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "USERS")
@Data
public class User extends MotherPersistent {

	@Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLES")
    private List<Role> roles;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BankingAccount> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Category> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "User [id=" + this.id + ", roles=" + roles + ", userName=" + userName + "]";
    }

    

}
