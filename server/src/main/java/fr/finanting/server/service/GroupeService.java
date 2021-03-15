package fr.finanting.server.service;

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

/**
 * Service for groups
 */
public interface GroupeService {

    /**
     * Method used to create a groupe
     */
    public GroupeDTO createGroupe(GroupeCreationParameter groupeCreationParameter, String userName)
        throws GroupeNameAlreadyExistException, UserNotExistException;

    /**
     * Method used to add a user to a group
     */
    public GroupeDTO addUsersGroupe(AddUsersGroupeParameter addUsersGroupeParameter, String userName)
        throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException;

    /**
     * Method used to remove a user from a groupe
     */
    public GroupeDTO removeUsersGroupe(RemoveUsersGroupeParameter removeUsersGroupeParameter, String userName)
        throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException;

    /**
     * Method used to delete a group
     */
    public void deleteGroupe(DeleteGroupeParameter deleteGroupeParameter, String userName)
        throws GroupeNotExistException, NotAdminGroupeException;

}
