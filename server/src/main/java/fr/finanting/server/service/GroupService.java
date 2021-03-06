package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.generated.model.AddUsersGroupParameter;
import fr.finanting.server.generated.model.GroupDTO;
import fr.finanting.server.generated.model.GroupParameter;
import fr.finanting.server.generated.model.RemoveUsersGroupParameter;
import fr.finanting.server.exception.GroupNameAlreadyExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.NotAdminGroupException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;


public interface GroupService {

    GroupDTO createGroup(GroupParameter groupParameter, String userName)
        throws GroupNameAlreadyExistException, UserNotExistException;

    GroupDTO addUsersGroup(AddUsersGroupParameter addUsersGroupParameter, String userName)
        throws UserNotExistException, GroupNotExistException, NotAdminGroupException;

    GroupDTO removeUsersGroup(RemoveUsersGroupParameter removeUsersGroupParameter, String userName)
        throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException;

    void deleteGroup(Integer groupId, String userName)
        throws GroupNotExistException, NotAdminGroupException;

    List<GroupDTO> getUserGroups(final String userName);

    GroupDTO getGroup(final Integer groupId, final String userName)
            throws GroupNotExistException, UserNotInGroupException;
}
