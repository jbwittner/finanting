package fr.finanting.server.dto;

import fr.finanting.server.model.embeddable.Address;
import lombok.Data;

@Data
public class AddressDTO {
    
    private String address;
    private String street;
    private String city;
    private Integer zipCode;

    public AddressDTO(Address address){
        this.address = address.getAddress();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
    }

}
