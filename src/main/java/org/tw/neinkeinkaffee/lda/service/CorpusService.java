package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.CorpusName;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.repository.CorpusDocumentRepository;
import org.tw.neinkeinkaffee.lda.repository.CorpusNameRepository;

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
		if (corpusDocumentRepository.findAllByCorpusName(corpus.getName()).isEmpty()) {
			for (CorpusDocument document : corpus.getDocuments()) {
				document.setCorpusName(corpus.getName());
				corpusDocumentRepository.save(document);
			}
			corpusNameRepository.save(CorpusName.builder().name(corpus.getName()).build());
		}
	}

	// TODO: Could get rid of this and ldaParameterCombinations with lite objects (https://stackoverflow.com/questions/25589113/how-to-select-a-single-field-in-mongodb)?
	public List<CorpusName> fetchAll() {
		return corpusNameRepository.findAll();
	}

	public void clearAll() {
		corpusDocumentRepository.deleteAll();
		corpusNameRepository.deleteAll();
	}
}
