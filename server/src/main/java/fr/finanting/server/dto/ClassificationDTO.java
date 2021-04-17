package fr.finanting.server.dto;

import fr.finanting.server.model.Classification;
import lombok.Data;

@Data
public class ClassificationDTO {

    private Integer id;
    private String label;
    private String abbreviation;
    private String descritpion;

    public ClassificationDTO(final Classification classification){
        this.id = classification.getId();
        this.label = classification.getLabel();
        this.abbreviation = classification.getAbbreviation();
        this.descritpion = classification.getDescritpion();
    }
    
}
