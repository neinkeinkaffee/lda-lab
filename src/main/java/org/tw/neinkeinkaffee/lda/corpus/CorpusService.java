package org.tw.neinkeinkaffee.lda.corpus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.corpus.CorpusName;
import org.tw.neinkeinkaffee.lda.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.corpus.CorpusDocumentRepository;
import org.tw.neinkeinkaffee.lda.corpus.CorpusNameRepository;

import java.util.List;

@Service
public class CorpusService {

	private CorpusDocumentRepository corpusDocumentRepository;
	private CorpusNameRepository corpusNameRepository;

	@Autowired
	public CorpusService(CorpusDocumentRepository corpusDocumentRepository, CorpusNameRepository corpusNameRepository) {
		this.corpusDocumentRepository = corpusDocumentRepository;
		this.corpusNameRepository = corpusNameRepository;
	}

	public Corpus fetchBy(String name) {
		return Corpus.builder()
		.name(name)
		.documents(corpusDocumentRepository.findAllByCorpusName(name))
		.build();
	}

    public void save(Corpus corpus) {
	    if (!corpusNameRepository.existsByName(corpus.getName())) {
		    corpusNameRepository.save(CorpusName.builder().name(corpus.getName()).build());
		    for (CorpusDocument document : corpus.getDocuments()) {
			    document.setCorpusName(corpus.getName());
			    corpusDocumentRepository.save(document);
		    }
	    }
    }

	// TODO: Could get rid of this and ldaParameterCombinations with lite objects (https://stackoverflow.com/questions/25589113/how-to-select-a-single-field-in-mongodb)?
    public List<CorpusName> fetchAll() {
	    return corpusNameRepository.findAll();
    }

    public void clearByName(String name) {
		corpusNameRepository.deleteByName(name);
		corpusDocumentRepository.deleteAllByCorpusName(name);
    }

	public void clearAll() {
		corpusDocumentRepository.deleteAll();
		corpusNameRepository.deleteAll();
	}
}
