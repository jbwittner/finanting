package fr.finanting.server.controller;

import fr.finanting.codegen.api.ThirdApi;
import fr.finanting.codegen.model.ThirdDTO;
import fr.finanting.codegen.model.ThirdParameter;
import fr.finanting.codegen.model.UpdateThirdParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.service.ThirdService;

import java.util.List;

@RestController
public class ThirdController extends MotherController implements ThirdApi {

    private final ThirdService thirdService;

    @Autowired
    public ThirdController(final ThirdService thirdService){
        super();
        this.thirdService = thirdService;
    }

    @Override
    public ResponseEntity<Void> createThird(final ThirdParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.thirdService.createThird(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteThird(final Integer thirdId) {
        final String userName = this.getCurrentPrincipalName();
        this.thirdService.deleteThird(thirdId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ThirdDTO>> getGroupThird(final Integer groupId) {
        final String userName = this.getCurrentPrincipalName();
        final List<ThirdDTO> thirdDTOList = this.thirdService.getGroupThird(groupId, userName);
        return new ResponseEntity<>(thirdDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ThirdDTO>> getUserThird() {
        final String userName = this.getCurrentPrincipalName();
        final List<ThirdDTO> thirdDTOList = this.thirdService.getUserThird(userName);
        return new ResponseEntity<>(thirdDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateThird(final Integer thirdId, final UpdateThirdParameter body) {
        final String userName = this.getCurrentPrincipalName();
        this.thirdService.updateThird(thirdId, body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
