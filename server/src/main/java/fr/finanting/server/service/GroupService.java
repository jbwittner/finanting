package fr.finanting.server.service;

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

/**
 * Service for groups
 */
public interface GroupService {

    /**
     * Method used to create a group
     */
    public GroupDTO createGroup(GroupCreationParameter groupCreationParameter, String userName)
        throws GroupNameAlreadyExistException, UserNotExistException;

    /**
     * Method used to add a user to a group
     */
    public GroupDTO addUsersGroup(AddUsersGroupParameter addUsersGroupParameter, String userName)
        throws UserNotExistException, GroupNotExistException, NotAdminGroupException;

    /**
     * Method used to remove a user from a group
     */
    public GroupDTO removeUsersGroup(RemoveUsersGroupParameter removeUsersGroupParameter, String userName)
        throws GroupNotExistException, NotAdminGroupException, UserNotInGroupException, UserNotExistException;

    /**
     * Method used to delete a group
     */
    public void deleteGroup(DeleteGroupParameter deleteGroupParameter, String userName)
        throws GroupNotExistException, NotAdminGroupException;

}
