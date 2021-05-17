package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.AddressDTO;
import fr.finanting.server.model.embeddable.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDTOBuilder extends Transformer<Address, AddressDTO> {

    @Override
    public AddressDTO transform(Address input) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress(input.getAddress());
        addressDTO.setCity(input.getCity());
        addressDTO.setStreet(input.getStreet());
        addressDTO.setZipCode(input.getZipCode());
        return addressDTO;
    }

    @Override
    public List<AddressDTO> transformAll(List<Address> input) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        input.forEach(address -> addressDTOList.add(this.transform(address)));
        return addressDTOList;
    }
}
