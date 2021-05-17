package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.server.codegen.api.ClassificationApi;

import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.codegen.model.ClassificationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import fr.finanting.server.service.ClassificationService;

@RestController
public class ClassificationController extends MotherController implements ClassificationApi {

    private ClassificationService classificationService;

    @Autowired
    public ClassificationController(final ClassificationService classificationService){
        this.classificationService = classificationService;
    }

    @Override
    public ResponseEntity<Void> createClassification(ClassificationParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.classificationService.createClassification(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteClassification(Integer classificationId) {
        final String userName = this.getCurrentPrincipalName();
        this.classificationService.deleteClassification(classificationId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClassificationDTO>> getGroupClassifications(Integer groupId) {
        return null;
    }

    @Override
    public ResponseEntity<List<ClassificationDTO>> getUserClassification() {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateClassification(Integer classificationId, ClassificationParameter body) {
        return null;
    }



    /*
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

     */
}
