package fr.finanting.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "FILES")
@Data
public class Files extends MotherPersistant {

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Lob
    @Column(name = "FILE", nullable = false)
    private byte[] file;
    
}
