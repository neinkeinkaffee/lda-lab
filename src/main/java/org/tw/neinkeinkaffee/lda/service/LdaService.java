package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.helper.ToyDataProvider;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.repository.LdaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LdaService {
	private LdaRepository ldaRepository;
    private ToyDataProvider toyDataProvider;

    @Autowired
    public LdaService(final LdaRepository ldaRepository, final ToyDataProvider provider) {
        this.ldaRepository = ldaRepository;
    	this.toyDataProvider = provider;
    }

    public DtoLda fetchBy(String corpusName, int numberOfTopics) {
    	if (corpusName.equals("toyCorpus")) {
		    return toyDataProvider.produceToyLda("toyCorpus");
	    }
	    Lda lda = ldaRepository.findByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
	    return lda.getDtoLda();
    }

	public List<DtoLda> fetchAll() {
    	return ldaRepository.findAll().stream()
		    .map(lda -> lda.getDtoLda())
		    .collect(Collectors.toList());
	}

	public void save(Lda lda) { ldaRepository.save(lda); }

	public void clearAll() {
    	ldaRepository.deleteAll();
	}
}
