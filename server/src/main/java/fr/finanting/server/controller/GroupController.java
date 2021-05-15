package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.server.codegen.api.GroupApi;
import fr.finanting.server.codegen.model.GroupDTO;
import fr.finanting.server.codegen.model.GroupParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.finanting.server.service.GroupService;

@RestController
public class GroupController extends MotherController implements GroupApi {

    protected final GroupService groupService;

    @Autowired
    public GroupController(final GroupService groupService) {
        this.groupService = groupService;
    }

    /*
    @PostMapping("/createGroup")
    public GroupDTO createGroup(final Authentication authentication,
                                @RequestBody final GroupCreationParameter groupCreationParameter)
            throws GroupNameAlreadyExistException, UserNotExistException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.createGroup(groupCreationParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteGroup")
    public void deleteGroup(final Authentication authentication,
                            @RequestBody final DeleteGroupParameter deleteGroupParameter)
            throws GroupNotExistException, NotAdminGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.groupService.deleteGroup(deleteGroupParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/addUsersGroup")
    public GroupDTO addUsersGroup(final Authentication authentication,
                                  @RequestBody final AddUsersGroupParameter addUsersGroupParameter)
            throws UserNotExistException, GroupNotExistException, NotAdminGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.addUsersGroup(addUsersGroupParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/removeUsersGroup")
    public GroupDTO removeUsersGroup(final Authentication authentication,
                                     @RequestBody final RemoveUsersGroupParameter removeUsersGroupParameter)
            throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.removeUsersGroup(removeUsersGroupParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getUserGroups")
    public List<GroupDTO> getUserGroups(final Authentication authentication) {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.getUserGroups(userDetailsImpl.getUsername());
    }

    @GetMapping("/getBankingAccount/{groupName}")
    public GroupDTO getGroup(final Authentication authentication,
                              @PathVariable final String groupName)
            throws UserNotInGroupException, GroupNotExistException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.groupService.getGroup(groupName, userDetailsImpl.getUsername());
    }

     */

    @Override
    public ResponseEntity<GroupDTO> createGroup(GroupParameter body) {
        String userName = this.getCurrentPrincipalName();
        GroupDTO groupDTO = this.groupService.createGroup(body, userName);
        return null;
    }
}
