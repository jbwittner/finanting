package fr.finanting.server.service;

import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateClassificationParameter;
import fr.finanting.server.parameter.DeleteClassificationParameter;
import fr.finanting.server.parameter.UpdateClassificationParameter;

public interface ClassificationService {

    public void createClassification(final CreateClassificationParameter createClassificationParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException;

    public void updateClassification(final UpdateClassificationParameter updateClassificationParameter, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;

    public void deleteClassification(final DeleteClassificationParameter deleteClassificationParameter, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;
    
}
