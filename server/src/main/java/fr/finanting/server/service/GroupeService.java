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

public interface GroupeService {

    public GroupeDTO createGroupe(GroupeCreationParameter groupeCreationParameter, String userName) throws GroupeNameAlreadyExistException, UserNotExistException;

    public GroupeDTO addUsersGroupe(AddUsersGroupeParameter addUsersGroupeParameter, String userName) throws UserNotExistException, GroupeNotExistException, NotAdminGroupeException;

    public GroupeDTO removeUsersGroupe(RemoveUsersGroupeParameter removeUsersGroupeParameter, String userName) throws GroupeNotExistException, NotAdminGroupeException, UserNotInGroupeException, UserNotExistException;
    
    public void deleteGroupe(DeleteGroupeParameter deleteGroupeParameter, String userName) throws GroupeNotExistException, NotAdminGroupeException;

}
