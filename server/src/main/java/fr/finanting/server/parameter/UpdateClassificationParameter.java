package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateClassificationParameter {

    @NotNull
    private Integer id;

    @NotNull
    @NotEmpty
    private String label;

    @NotNull
    @NotEmpty
    private String abbreviation;

    private String descritpion;

}
