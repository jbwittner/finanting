package fr.finanting.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.ClassificationDTO;
import fr.finanting.server.exception.ClassificationNoUserException;
import fr.finanting.server.exception.ClassificationNotExistException;
import fr.finanting.server.exception.GroupNotExistException;
import fr.finanting.server.exception.UserNotInGroupException;
import fr.finanting.server.parameter.CreateClassificationParameter;
import fr.finanting.server.parameter.DeleteClassificationParameter;
import fr.finanting.server.parameter.UpdateClassificationParameter;
import fr.finanting.server.security.UserDetailsImpl;
import fr.finanting.server.service.ClassificationService;

@RestController
@RequestMapping("classification")
public class ClassificationController {

    private ClassificationService classificationService;

    @Autowired
    public ClassificationController(final ClassificationService classificationService){
        this.classificationService = classificationService;
    }
    
    @PostMapping("/createClassification")
    public void createClassification(final Authentication authentication,
                                    @RequestBody final CreateClassificationParameter createClassificationParameter) 
            throws GroupNotExistException, UserNotInGroupException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.classificationService.createClassification(createClassificationParameter, userDetailsImpl.getUsername());
    }

    @PostMapping("/updateClassification")
    public void updateClassification(final Authentication authentication,
                                    @RequestBody final UpdateClassificationParameter updateClassificationParameter) 
            throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.classificationService.updateClassification(updateClassificationParameter, userDetailsImpl.getUsername());
    }

    @DeleteMapping("/deleteClassification")
    public void deleteClassification(final Authentication authentication,
                                    @RequestBody final DeleteClassificationParameter deleteClassificationParameter) 
            throws ClassificationNotExistException, UserNotInGroupException, ClassificationNoUserException{
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        this.classificationService.deleteClassification(deleteClassificationParameter, userDetailsImpl.getUsername());
    }

    @GetMapping("/getGroupClassifications/{groupName}")
    public List<ClassificationDTO> getGroupCategory(final Authentication authentication, 
                                            @PathVariable final String groupName)
            throws GroupNotExistException, UserNotInGroupException {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.classificationService.getGroupClassifications(groupName, userDetailsImpl.getUsername());
    }

    @GetMapping("/getUserClassifications")
    public List<ClassificationDTO> getUserCategory(final Authentication authentication) {
        final UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return this.classificationService.getUserClassifications(userDetailsImpl.getUsername());
    }
}
