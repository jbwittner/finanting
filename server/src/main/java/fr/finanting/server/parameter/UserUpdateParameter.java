package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Parameter used to update account informations
 */
@Data
public class UserUpdateParameter {

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;
    
}
