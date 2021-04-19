package fr.finanting.server.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact {

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "PORTABLE_PHONE")
    private String portablePhone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEBSITE")
    private String website;
    
}
