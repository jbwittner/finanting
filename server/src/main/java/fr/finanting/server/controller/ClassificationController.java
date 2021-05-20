package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.server.codegen.api.ClassificationApi;

import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.codegen.model.ClassificationParameter;
import fr.finanting.server.codegen.model.UpdateClassificationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import fr.finanting.server.service.ClassificationService;

@RestController
public class ClassificationController extends MotherController implements ClassificationApi {

    private final ClassificationService classificationService;

    @Autowired
    public ClassificationController(final ClassificationService classificationService){
        super();
        this.classificationService = classificationService;
    }

    @Override
    public ResponseEntity<Void> createClassification(final ClassificationParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.classificationService.createClassification(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteClassification(final Integer classificationId) {
        final String userName = this.getCurrentPrincipalName();
        this.classificationService.deleteClassification(classificationId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClassificationDTO>> getGroupClassifications(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        final List<ClassificationDTO> classificationDTOList = this.classificationService.getGroupClassifications(groupId, userName);
        return new ResponseEntity<>(classificationDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ClassificationDTO>> getUserClassification() {
        final String userName = this.getCurrentPrincipalName();
        final List<ClassificationDTO> classificationDTOList = this.classificationService.getUserClassifications(userName);
        return new ResponseEntity<>(classificationDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateClassification(final Integer classificationId, final UpdateClassificationParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.classificationService.updateClassification(classificationId, body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
