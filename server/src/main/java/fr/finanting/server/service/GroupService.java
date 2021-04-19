package fr.finanting.server.service;

import java.util.List;

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

public interface GroupService {

    public GroupDTO createGroup(GroupCreationParameter groupCreationParameter, String userName)
        throws GroupNameAlreadyExistException, UserNotExistException;

    public GroupDTO addUsersGroup(AddUsersGroupParameter addUsersGroupParameter, String userName)
        throws UserNotExistException, GroupNotExistException, NotAdminGroupException;

    public GroupDTO removeUsersGroup(RemoveUsersGroupParameter removeUsersGroupParameter, String userName)
        throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException;

    public void deleteGroup(DeleteGroupParameter deleteGroupParameter, String userName)
        throws GroupNotExistException, NotAdminGroupException;

    public List<GroupDTO> getUserGroups(final String userName);

    public GroupDTO getGroup(final String groupName, final String userName)
            throws GroupNotExistException, UserNotInGroupException;
}
