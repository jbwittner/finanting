package fr.finanting.server.service;

import fr.finanting.server.exception.CategoryNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.ThirdNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateThirdParameter;
import fr.finanting.server.parameter.UpdateThirdParameter;

public interface ThirdService {

    public void createThird(final CreateThirdParameter createThirdParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException, CategoryNotExistException;

    public void updateThrid(final UpdateThirdParameter updateThirdParameter, final String userName)
        throws CategoryNotExistException, ThirdNotExistException, UserNotInGroupException;
    
}
