package fr.finanting.server.dto;

import fr.finanting.codegen.model.ContactDTO;
import fr.finanting.server.model.embeddable.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDTOBuilder implements Transformer<Contact, ContactDTO> {
    @Override
    public ContactDTO transform(final Contact input) {
        final ContactDTO contactDTO = new ContactDTO();
        contactDTO.setEmail(input.getEmail());
        contactDTO.setHomePhone(input.getHomePhone());
        contactDTO.setPortablePhone(input.getPortablePhone());
        contactDTO.setWebsite(input.getWebsite());
        return contactDTO;
    }

    @Override
    public List<ContactDTO> transformAll(final List<Contact> input) {
        final List<ContactDTO> contactDTOList = new ArrayList<>();
        input.forEach(contact -> contactDTOList.add(this.transform(contact)));
        return contactDTOList;
    }
}
