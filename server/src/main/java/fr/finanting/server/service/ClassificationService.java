package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.codegen.model.ClassificationParameter;
import fr.finanting.server.dto.ClassificationDTO;
import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.DeleteClassificationParameter;
import fr.finanting.server.parameter.UpdateClassificationParameter;

public interface ClassificationService {

    void createClassification(final ClassificationParameter classificationParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException;

    void updateClassification(final UpdateClassificationParameter updateClassificationParameter, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;

    void deleteClassification(final DeleteClassificationParameter deleteClassificationParameter, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;

    List<ClassificationDTO> getUserClassifications(final String userName);

    List<ClassificationDTO> getGroupClassifications(final String groupName, final String userName)
        throws GroupNotExistException, UserNotInGroupException;

    
}
