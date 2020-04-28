package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract part of models
 */
@MappedSuperclass
public abstract class AbstractPersistant {

    @Id
    @Column(name = "ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    /**
     * Getter of id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of id
     * @param id id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Override of toString method
     * @return String representation of the model
     */
    @Override
    public abstract String toString();

}