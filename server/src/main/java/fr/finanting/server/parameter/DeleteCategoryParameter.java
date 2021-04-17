package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteCategoryParameter {

    @NotEmpty
    @NotNull
    private Integer id;

}
