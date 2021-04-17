package fr.finanting.server.model.embeddable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact {

    private String homePhone;
    private String portablePhone;
    private String email;
    private String website;
    
}
