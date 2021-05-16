package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.server.codegen.api.GroupApi;
import fr.finanting.server.codegen.model.AddUsersGroupParameter;
import fr.finanting.server.codegen.model.GroupDTO;
import fr.finanting.server.codegen.model.GroupParameter;
import fr.finanting.server.codegen.model.RemoveUsersGroupParameter;
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
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<GroupDTO> addUserGroup(AddUsersGroupParameter body) {
        String userName = this.getCurrentPrincipalName();
        GroupDTO groupDTO = this.groupService.addUsersGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> createGroup(GroupParameter body) {
        String userName = this.getCurrentPrincipalName();
        GroupDTO groupDTO = this.groupService.createGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteGroup(Integer groupId) {
        String userName = this.getCurrentPrincipalName();
        this.groupService.deleteGroup(groupId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> getGroup(Integer groupId) {
        String userName = this.getCurrentPrincipalName();
        GroupDTO groupDTO = this.groupService.getGroup(groupId, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GroupDTO>> getGroups() {
        String userName = this.getCurrentPrincipalName();
        List<GroupDTO> groupDTOList = this.groupService.getUserGroups(userName);
        return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDTO> removeUserGroup(RemoveUsersGroupParameter body) {
        String userName = this.getCurrentPrincipalName();
        GroupDTO groupDTO = this.groupService.removeUsersGroup(body, userName);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }
}
