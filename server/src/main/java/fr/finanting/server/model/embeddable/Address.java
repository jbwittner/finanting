package fr.finanting.server.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;
   
}