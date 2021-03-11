package fr.finanting.server.service;

import fr.finanting.server.dto.GroupeDTO;
import fr.finanting.server.exception.GroupeNameAlreadyExistException;
import fr.finanting.server.exception.UserNotExistException;
import fr.finanting.server.parameter.GroupeCreationParameter;

public interface GroupeService {

    public GroupeDTO createGroupe(GroupeCreationParameter groupeCreationParameter, String userName) throws GroupeNameAlreadyExistException, UserNotExistException;
    
}
