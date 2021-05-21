package fr.finanting.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import fr.finanting.server.model.mother.MotherGroupUserElement;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CATEGORIES")
@Data
public class Category extends MotherGroupUserElement {

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
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLES")
    private CategoryType categoryType;

    @Override
    public String toString() {
        return "Category [id= " + this.id + ", abbreviation=" + abbreviation + ", label=" + label + ", user=" + user + "]";
    }
    
}
