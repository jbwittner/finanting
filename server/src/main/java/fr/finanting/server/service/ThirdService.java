package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.dto.ThirdDTO;
import fr.finanting.server.exception.BadAssociationThirdException;
import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.ThirdNoUserException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateThirdParameter;
import fr.finanting.server.parameter.DeleteThirdParameter;
import fr.finanting.server.parameter.UpdateThirdParameter;

public interface ThirdService {

    public void createThird(final CreateThirdParameter createThirdParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException, BadAssociationThirdException;

    public void updateThrid(final UpdateThirdParameter updateThirdParameter, final String userName)
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException, BadAssociationThirdException, ThirdNoUserException;
    
    public void deleteThird(final DeleteThirdParameter deleteThirdParameter, final String userName)
        throws ThirdNotExistException, UserNotInGroupException, ThirdNoUserException;

    public List<ThirdDTO> getUserThird(final String userName);

    public List<ThirdDTO> getGroupThird(String groupName, String userName)
        throws UserNotInGroupException, GroupNotExistException;
}
