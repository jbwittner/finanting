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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GROUPES")
@Data
public class Groupe extends MotherPersistant {

	@Column(name = "GROUPE_NAME", nullable = false, unique = true)
    private String groupeName;

    @ManyToMany
    @JoinTable( name = "USERS_GROUPES_ASSOCICATIONS",
                joinColumns = @JoinColumn( name = "ID" ))
    @Column(name = "totototo")
    private List<User> users = new ArrayList<>();

}
