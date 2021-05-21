package fr.finanting.server.dto;

import fr.finanting.server.codegen.model.ClassificationDTO;
import fr.finanting.server.model.Classification;

import java.util.ArrayList;
import java.util.List;

public class ClassificationDTOBuilder implements Transformer<Classification, ClassificationDTO> {

    @Override
    public ClassificationDTO transform(final Classification input) {
        final ClassificationDTO classificationDTO = new ClassificationDTO();
        classificationDTO.setAbbreviation(input.getAbbreviation());
        classificationDTO.setDescription(input.getDescription());
        classificationDTO.setLabel(input.getLabel());
        classificationDTO.setId(input.getId());
        return classificationDTO;
    }

    @Override
    public List<ClassificationDTO> transformAll(final List<Classification> input) {
        final List<ClassificationDTO> classificationDTOList = new ArrayList<>();
        input.forEach(classification -> classificationDTOList.add(this.transform(classification)));
        return classificationDTOList;
    }

}
