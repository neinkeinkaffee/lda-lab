package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.helper.SyntheticDataProvider;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.repository.LdaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LdaService {
	private LdaRepository ldaRepository;
    private SyntheticDataProvider provider;

    @Autowired
    public LdaService(final LdaRepository ldaRepository, final SyntheticDataProvider provider) {
        this.ldaRepository = ldaRepository;
    	this.provider = provider;
    }

    public Lda fetchBy(UUID corpusId, int numberOfTopics) {
    	if (corpusId.toString().equals("c2cef191-e025-43bb-9021-6413335bbf5d")) {
		    return provider.getByCorpusIdAndNumberOfTopics(corpusId, numberOfTopics);
	    }
	    else {
    		return ldaRepository.findBy(corpusId, numberOfTopics);
	    }
    }

	public List<Lda> fetchAll() {
    	return ldaRepository.findAll();
	}
}
