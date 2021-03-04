package fr.finanting.server.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "USERS")
@Data
public class UserAuthorities extends AbstractPersistant {
    

    
}
