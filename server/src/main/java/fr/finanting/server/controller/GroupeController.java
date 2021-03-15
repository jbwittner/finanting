package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.GroupeDTO;
import fr.finanting.server.exception.GroupeNameAlreadyExistException;
import fr.finanting.server.exception.GroupeNotExistException;
import fr.finanting.server.exception.NotAdminGroupeException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupeException;
import fr.finanting.server.parameter.AddUsersGroupeParameter;
import fr.finanting.server.parameter.DeleteGroupeParameter;
import fr.finanting.server.parameter.GroupeCreationParameter;
import fr.finanting.server.parameter.RemoveUsersGroupeParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.GroupeService;

/**
 * Group controller
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
     * Endpoint used to create a groupe
     */
    @PostMapping("/createGroupe")
    public GroupeDTO createGroupe(final Authentication authentication, @RequestBody final GroupeCreationParameter groupeCreationParameter) throws GroupeNameAlreadyExistException, UserNotExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupeService.createGroupe(groupeCreationParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to delete a group
     */
    @DeleteMapping("/deleteGroupe")
    public void deleteGroupe(final Authentication authentication, @RequestBody final DeleteGroupeParameter deleteGroupeParameter) throws GroupeNotExistException, NotAdminGroupeException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.groupeService.deleteGroupe(deleteGroupeParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to add a user to a group
     */
    @PostMapping("/addUsersGroupe")
    public GroupeDTO addUsersGroupe(final Authentication authentication, @RequestBody final AddUsersGroupeParameter addUsersGroupeParameter) throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupeService.addUsersGroupe(addUsersGroupeParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to remove a user from a groupe
     */
    @PostMapping("/removeUsersGroupe")
    public GroupeDTO removeUsersGroupe(final Authentication authentication, @RequestBody final RemoveUsersGroupeParameter removeUsersGroupeParameter) throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupeService.removeUsersGroupe(removeUsersGroupeParameter, userDetailsImpl.getUsername());
    }

}
