package fr.finanting.server.service.classificationservice;

import java.util.ArrayList;
import java.util.List;

import fr.finanting.server.codegen.model.ClassificationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fr.finanting.server.model.Classification;
import fr.finanting.server.model.User;
import fr.finanting.server.repository.ClassificationRepository;
import fr.finanting.server.repository.GroupRepository;
import fr.finanting.server.repository.UserRepository;
import fr.finanting.server.service.implementation.ClassificationServiceImpl;
import fr.finanting.server.testhelper.AbstractMotherIntegrationTest;

public class TestGetUserClassifications extends AbstractMotherIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private ClassificationServiceImpl classificationServiceImpl;

    private int NUMBER_CLASSIFICATIONS = 4;


    @Override
    protected void initDataBeforeEach() throws Exception {
        this.classificationServiceImpl = new ClassificationServiceImpl(this.classificationRepository,
                                                                                this.groupRepository,
                                                                                this.userRepository);
    }

    @Test
    public void testGetUserClassifications(){

        final User user = this.testFactory.getUser();

        final List<Classification> classifications = new ArrayList<>();

        for(int index = 0; index < NUMBER_CLASSIFICATIONS; index ++){

            final Classification classification = this.testFactory.getClassification(user);
            classifications.add(classification);
            
        }

        final List<ClassificationDTO> classificationDTOs = this.classificationServiceImpl.getUserClassifications(user.getUserName());

        Assertions.assertEquals(NUMBER_CLASSIFICATIONS, classificationDTOs.size());

        for(final ClassificationDTO classificationDTO : classificationDTOs){
            boolean isPresent = false;

            for(final Classification classification : classifications){
                if(classification.getId().equals(classificationDTO.getId())){
                    isPresent = true;
                    Assertions.assertEquals(classificationDTO.getAbbreviation(), classification.getAbbreviation());
                    Assertions.assertEquals(classificationDTO.getDescription(), classification.getDescritpion());
                    Assertions.assertEquals(classificationDTO.getLabel(), classification.getLabel());
                }
            }

            Assertions.assertTrue(isPresent);
        }

    }
    
}
