package com.shopping.startup.service;


import com.shopping.startup.entity.Variation;
import com.shopping.startup.model.VariationRequest;
import com.shopping.startup.repository.VariationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VariationService {

    private final VariationRepository variationRepository;


    public List<Variation> getAllVariations() {
        return variationRepository.findAll();
    }


    public Variation addVariation(VariationRequest variationRequest) {
        if(variationRequest.getVariationName() == null || variationRequest.getVariationName().strip().length() == 0)
            return null;

        Variation variation = Variation.builder()
                .variationName(variationRequest.getVariationName().strip())
                .build();
        return variationRepository.save(variation);
    }

    @Transactional
    public Variation updateVariation(VariationRequest variationRequest) {
        if(variationRequest.getId() == null || variationRequest.getId().strip().length() == 0)
            return null;

        if(variationRequest.getVariationName() == null || variationRequest.getVariationName().strip().length() == 0)
            return null;

        Optional<Variation> variation = variationRepository.findById(Long.valueOf(variationRequest.getId().strip()));
        if(variation.isPresent()) {
            Variation updatedVariation = variation.get();
            updatedVariation.setVariationName(variationRequest.getVariationName().strip());

            return variationRepository.save(updatedVariation);
        }

        return null;
    }


    @Transactional
    public Variation deleteVariation(VariationRequest variationRequest) {
        if(variationRequest.getId() == null || variationRequest.getId().strip().length() == 0)
            return null;

        variationRepository.deleteById(Long.valueOf(variationRequest.getId().strip()));
        return Variation.builder().build();
    }
}
