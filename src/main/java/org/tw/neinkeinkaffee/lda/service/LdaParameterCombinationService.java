package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;
import org.tw.neinkeinkaffee.lda.repository.LdaParameterCombinationRepository;

import java.util.List;

@Service
public class LdaParameterCombinationService {
    private LdaParameterCombinationRepository ldaParameterCombinationRepository;

    @Autowired
    public LdaParameterCombinationService(LdaParameterCombinationRepository ldaParameterCombinationRepository) {
        this.ldaParameterCombinationRepository = ldaParameterCombinationRepository;
    }

    public List<LdaParameterCombination> fetchAll() {
        return ldaParameterCombinationRepository.findAll();
    }
}
