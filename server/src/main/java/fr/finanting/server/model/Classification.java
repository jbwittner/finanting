package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;


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
    
}
