package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.BankDetailsDTO;
import fr.finanting.server.codegen.model.ContactDTO;
import fr.finanting.server.model.embeddable.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDTOBuilder extends Transformer<Contact, ContactDTO> {
    @Override
    public ContactDTO transform(Contact input) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setEmail(input.getEmail());
        contactDTO.setHomePhone(input.getHomePhone());
        contactDTO.setPortablePhone(input.getPortablePhone());
        contactDTO.setWebsite(input.getWebsite());
        return contactDTO;
    }

    @Override
    public List<ContactDTO> transformAll(List<Contact> input) {
        List<ContactDTO> contactDTOList = new ArrayList<>();
        input.forEach(contact -> contactDTOList.add(this.transform(contact)));
        return contactDTOList;
    }
}
