package fr.finanting.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.GroupDTO;
import fr.finanting.server.exception.GroupNameAlreadyExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.AddUsersGroupParameter;
import fr.finanting.server.parameter.DeleteGroupParameter;
import fr.finanting.server.parameter.GroupCreationParameter;
import fr.finanting.server.parameter.RemoveUsersGroupParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.GroupService;

/**
 * Group controller
 */
@RestController
@RequestMapping("group")
public class GroupController {

    protected final GroupService groupService;

    /**
     * Constructor
     */
    @Autowired
    public GroupController(final GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Endpoint used to create a group
     */
    @PostMapping("/createGroup")
    public GroupDTO createGroup(final Authentication authentication, @RequestBody final GroupCreationParameter groupCreationParameter) throws GroupNameAlreadyExistException, UserNotExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.createGroup(groupCreationParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to delete a group
     */
    @DeleteMapping("/deleteGroup")
    public void deleteGroup(final Authentication authentication, @RequestBody final DeleteGroupParameter deleteGroupParameter) throws GroupNotExistException, NotAdminGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.groupService.deleteGroup(deleteGroupParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to add a user to a group
     */
    @PostMapping("/addUsersGroup")
    public GroupDTO addUsersGroup(final Authentication authentication, @RequestBody final AddUsersGroupParameter addUsersGroupParameter) throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.addUsersGroup(addUsersGroupParameter, userDetailsImpl.getUsername());
    }

    /**
     * Endpoint used to remove a user from a group
     */
    @PostMapping("/removeUsersGroup")
    public GroupDTO removeUsersGroup(final Authentication authentication, @RequestBody final RemoveUsersGroupParameter removeUsersGroupParameter) throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.removeUsersGroup(removeUsersGroupParameter, userDetailsImpl.getUsername());
    }

}
