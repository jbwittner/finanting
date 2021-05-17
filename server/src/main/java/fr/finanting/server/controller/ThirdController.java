package fr.finanting.server.controller;

import fr.finanting.server.codegen.api.ThirdApi;
import fr.finanting.server.codegen.model.ThirdDTO;
import fr.finanting.server.codegen.model.ThirdParameter;
import fr.finanting.server.codegen.model.UpdateThirdParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.service.ThirdService;

import java.util.List;

@RestController
public class ThirdController extends MotherController implements ThirdApi {

    private ThirdService thirdService;

    @Autowired
    public ThirdController(final ThirdService thirdService){
        this.thirdService = thirdService;
    }

    @Override
    public ResponseEntity<Void> createThird(ThirdParameter body) {
        String userName = this.getCurrentPrincipalName();
        this.thirdService.createThird(body, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteThird(Integer thirdId) {
        String userName = this.getCurrentPrincipalName();
        this.thirdService.deleteThird(thirdId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ThirdDTO>> getGroupThird(Integer groupId) {
        String userName = this.getCurrentPrincipalName();
        List<ThirdDTO> thirdDTOList = this.thirdService.getGroupThird(groupId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ThirdDTO>> getUserThird() {
        String userName = this.getCurrentPrincipalName();
        List<ThirdDTO> thirdDTOList = this.thirdService.getUserThird(userName);
        return new ResponseEntity<>(thirdDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateThird(Integer thirdId, UpdateThirdParameter body) {
        String userName = this.getCurrentPrincipalName();
        this.thirdService.updateThird(thirdId, body, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
