package fr.finanting.server.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import fr.finanting.server.model.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.finanting.server.api.PetApi;
import fr.finanting.server.model.ModelApiResponse;
import fr.finanting.server.model.Pet;

@RestController
@RequestMapping
public class TestController implements PetApi{

    @Override
    public ResponseEntity<Void> addPet(@Valid Pet body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByStatus(@NotNull @Valid List<String> status) {
        // TODO Auto-generated method stub
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByTags(@NotNull @Valid List<String> tags) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Test> getPetById(Long petId) {
        Test newTest = new Test();
        newTest.setId(0L);
        newTest.setUsername("Name");
        ResponseEntity<Test> responseEntity = new ResponseEntity<>(newTest, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/toto")
    public ResponseEntity<String> getToto() {
        return new ResponseEntity<>("petId.toString()", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updatePet(@Valid Pet body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata,
            @Valid MultipartFile file) {
        // TODO Auto-generated method stub
        return null;
    }
}
