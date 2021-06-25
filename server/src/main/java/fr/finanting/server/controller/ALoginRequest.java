package fr.finanting.server.controller;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ALoginRequest {
 
    @NotNull
    private String userName;
 
    @NotNull
    private String password;
}
