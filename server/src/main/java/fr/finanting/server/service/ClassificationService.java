package fr.finanting.server.service;

import java.util.List;

import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.codegen.model.ClassificationParameter;
import fr.finanting.server.codegen.model.UpdateClassificationParameter;
import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;

public interface ClassificationService {

    void createClassification(final ClassificationParameter classificationParameter, final String userName)
        throws GroupNotExistException, UserNotInGroupException;

    void updateClassification(Integer classificationId, final UpdateClassificationParameter updateClassificationParameter, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;

    void deleteClassification(final Integer classificationId, final String userName)
        throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException;

    List<ClassificationDTO> getUserClassifications(final String userName);

    List<ClassificationDTO> getGroupClassifications(final Integer groupId, final String userName)
        throws GroupNotExistException, UserNotInGroupException;

    
}
