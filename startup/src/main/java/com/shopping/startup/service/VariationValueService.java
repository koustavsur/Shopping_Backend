package com.shopping.startup.service;


import com.shopping.startup.entity.Variation;
import com.shopping.startup.entity.VariationValue;
import com.shopping.startup.model.VariationValueRequest;
import com.shopping.startup.repository.VariationRepository;
import com.shopping.startup.repository.VariationValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationValueService {

    private final VariationRepository variationRepository;
    private final VariationValueRepository variationValueRepository;


    public List<VariationValue> getAllVariationValues() {

        return variationValueRepository.findAll();
    }


    public VariationValue addVariationValue(VariationValueRequest variationValueRequest) {
        if(variationValueRequest.getVariationValueName() == null || variationValueRequest.getVariationValueName().strip().length() == 0)
            return null;

        if(variationValueRequest.getVariationId() == null || variationValueRequest.getVariationId().strip().length() == 0)
            return null;

        Optional<Variation> variation = variationRepository.findById(Long.valueOf(variationValueRequest.getVariationId().strip()));
        if(variation.isPresent()){
            VariationValue variationValue = VariationValue.builder()
                    .variationValueName(variationValueRequest.getVariationValueName().strip())
                    .variation(variation.get())
                    .build();

            return variationValueRepository.save(variationValue);
        }
        return null;
    }


    @Transactional
    public VariationValue updateVariationValue(VariationValueRequest variationValueRequest) {
        if(variationValueRequest.getId() == null || variationValueRequest.getId().strip().length() == 0)
            return null;

        if(variationValueRequest.getVariationValueName() == null || variationValueRequest.getVariationValueName().strip().length() == 0)
            return null;

        if(variationValueRequest.getVariationId() == null || variationValueRequest.getVariationId().strip().length() == 0)
            return null;

        Optional<VariationValue> variationValue = variationValueRepository.findById(Long.valueOf(variationValueRequest.getId().strip()));
        if(variationValue.isEmpty())
            return null;

        Optional<Variation> variation = variationRepository.findById(Long.valueOf(variationValueRequest.getVariationId().strip()));
        if(variation.isEmpty())
            return null;

        VariationValue updatedVariationValue = variationValue.get();

        updatedVariationValue.setVariationValueName(variationValueRequest.getVariationValueName().strip());
        updatedVariationValue.setVariation(variation.get());

        return variationValueRepository.save(updatedVariationValue);



    }

    @Transactional
    public VariationValue deleteVariation(VariationValueRequest variationValueRequest) {

        if(variationValueRequest.getId() == null || variationValueRequest.getId().strip().length() == 0)
            return null;

        variationValueRepository.deleteById(Long.valueOf(variationValueRequest.getId().strip()));
        return VariationValue.builder().build();
    }
}
