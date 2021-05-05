package fr.finanting.server.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.finanting.server.swagger2java.api.PetApi;
import fr.finanting.server.swagger2java.model.ModelApiResponse;
import fr.finanting.server.swagger2java.model.Pet;

@RestController
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
        return null;
    }

    @Override
    public ResponseEntity<List<Pet>> findPetsByTags(@NotNull @Valid List<String> tags) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        Pet newPet = new Pet();
        newPet.setName("Name");
        ResponseEntity<Pet> responseEntity = new ResponseEntity<Pet>(newPet, HttpStatus.OK);
        return responseEntity;
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
