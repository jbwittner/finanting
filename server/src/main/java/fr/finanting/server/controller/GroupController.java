package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.codegen.api.GroupApi;
import fr.finanting.codegen.model.AddUsersGroupParameter;
import fr.finanting.codegen.model.GroupDTO;
import fr.finanting.codegen.model.GroupParameter;
import fr.finanting.codegen.model.RemoveUsersGroupParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.finanting.server.service.GroupService;

@RestController
public class GroupController extends MotherController implements GroupApi {

    protected final GroupService groupService;

    @Autowired
    public GroupController(final GroupService groupService) {
        super();
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<GroupDTO> addUserGroup(final AddUsersGroupParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final GroupDTO groupDTO = this.groupService.addUsersGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> createGroup(final GroupParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final GroupDTO groupDTO = this.groupService.createGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        this.groupService.deleteGroup(groupId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> getGroup(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        final GroupDTO groupDTO = this.groupService.getGroup(groupId, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GroupDTO>> getGroups() {
        final String userName = this.getCurrentPrincipalName();
        final List<GroupDTO> groupDTOList = this.groupService.getUserGroups(userName);
        return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> removeUserGroup(final RemoveUsersGroupParameter body) {
        final String userName = this.getCurrentPrincipalName();
        final GroupDTO groupDTO = this.groupService.removeUsersGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }
}
