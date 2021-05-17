package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.model.Classification;

import java.util.ArrayList;
import java.util.List;

public class ClassificationDTOBuilder extends Transformer<Classification, ClassificationDTO> {

    @Override
    public ClassificationDTO transform(Classification input) {
        ClassificationDTO classificationDTO = new ClassificationDTO();
        classificationDTO.setAbbreviation(input.getAbbreviation());
        classificationDTO.setDescription(input.getDescritpion());
        classificationDTO.setLabel(input.getLabel());
        classificationDTO.setId(input.getId());
        return classificationDTO;
    }

    @Override
    public List<ClassificationDTO> transformAll(List<Classification> input) {
        List<ClassificationDTO> classificationDTOList = new ArrayList<>();
        input.forEach(classification -> classificationDTOList.add(this.transform(classification)));
        return classificationDTOList;
    }

}
