package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import fr.finanting.server.model.mother.MotherGroupUserElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CLASSIFICATIONS")
@Data
public class Classification extends MotherGroupUserElement {

    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "ABBREVIATION", nullable = false, length = 6)
    private String abbreviation;

    @Column(name = "DESCRIPTION")
    private String descritpion;
    
}
