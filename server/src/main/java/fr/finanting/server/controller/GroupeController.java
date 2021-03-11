package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.GroupeDTO;
import fr.finanting.server.dto.UserDTO;
import fr.finanting.server.exception.BadPasswordException;
import fr.finanting.server.exception.GroupeNameAlreadyExistException;
import fr.finanting.server.exception.UserEmailAlreadyExistException;
import fr.finanting.server.exception.UserNameAlreadyExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.parameter.GroupeCreationParameter;
import fr.finanting.server.parameter.PasswordUpdateParameter;
import fr.finanting.server.parameter.UserRegisterParameter;
import fr.finanting.server.parameter.UserUpdateParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.GroupeService;
import fr.finanting.server.service.UserService;

/**
 * User controller
 */
@RestController
@RequestMapping("groupe")
public class GroupeController {

    protected final GroupeService groupeService;

    /**
     * Constructor
     */
    @Autowired
    public GroupeController(final GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    /**
     * Endpoint used to register a new account
     * @throws GroupeNameAlreadyExistException
     * @throws UserNotExistException
     */
    @PostMapping("/createGroupe")
    public GroupeDTO createGroupe(final Authentication authentication, @RequestBody final GroupeCreationParameter groupeCreationParameter) throws GroupeNameAlreadyExistException, UserNotExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupeService.createGroupe(groupeCreationParameter, userDetailsImpl.getUsername());
    }

}
