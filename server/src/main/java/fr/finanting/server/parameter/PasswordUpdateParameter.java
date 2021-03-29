package fr.finanting.server.parameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PasswordUpdateParameter {
    
    @NotNull
    @NotEmpty
    private String previousPassword;

    @NotNull
    @NotEmpty
    private String newPassword;

}
