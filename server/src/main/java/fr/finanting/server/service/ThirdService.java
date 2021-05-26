package fr.finanting.server.service;

import java.util.List;

import fr.finanting.codegen.model.ThirdDTO;
import fr.finanting.codegen.model.ThirdParameter;
import fr.finanting.codegen.model.UpdateThirdParameter;
import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.ThirdNoUserException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;

public interface ThirdService {

    void createThird(final ThirdParameter thirdParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException;

    void updateThird(final Integer thirdId, final UpdateThirdParameter updateThirdParameter, final String userName)
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException;
    
    void deleteThird(final Integer thirdId, final String userName)
        throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException;

    List<ThirdDTO> getUserThird(final String userName);

    List<ThirdDTO> getGroupThird(Integer groupId, String userName)
        throws UserNotInGroupException, GroupNotExistException;
}
