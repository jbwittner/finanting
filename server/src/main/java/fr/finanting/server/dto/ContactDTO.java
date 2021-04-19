package fr.finanting.server.dto;

import fr.finanting.server.model.embeddable.Contact;
import lombok.Data;

@Data
public class ContactDTO {
    
    private String homePhone;
    private String portablePhone;
    private String email;
    private String website;

    public ContactDTO(final Contact contact){
        this.homePhone = contact.getHomePhone();
        this.portablePhone = contact.getPortablePhone();
        this.email = contact.getEmail();
        this.website = contact.getWebsite();
    }

}
