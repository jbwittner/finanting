package fr.finanting.server.parameter.subpart;

import lombok.Data;

@Data
public class AddressParameter {

    private String address;
    private String street;
    private String city;
    private Integer zipCode;
    
}
