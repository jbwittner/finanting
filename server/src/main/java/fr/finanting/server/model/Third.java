package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.model.embeddable.Address;
import fr.finanting.server.model.embeddable.BankDetails;
import fr.finanting.server.model.embeddable.Contact;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "THIRDS")
@Data
public class Third extends MotherPersistant {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category defaultCategory;

    @Embedded
    private BankDetails bankDetails;
    
    @Embedded
    private Address address;

    @Embedded
    private Contact contact;
    
}
