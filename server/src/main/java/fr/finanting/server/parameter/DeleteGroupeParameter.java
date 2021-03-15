package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used delete a group
 */
@Data
public class DeleteGroupParameter {

    @NotEmpty
    @NotNull
    private String groupName;

}
