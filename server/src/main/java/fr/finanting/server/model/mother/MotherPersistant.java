package fr.finanting.server.model.mother;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class MotherPersistant {

    @Id
    @Column(name = "ID", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

}