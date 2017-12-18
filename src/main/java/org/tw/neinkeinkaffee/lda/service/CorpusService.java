package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.repository.CorpusRepository;

import java.util.List;

@Service
public class CorpusService {

	private CorpusRepository corpusRepository;

	@Autowired
	public CorpusService(CorpusRepository corpusRepository) {
		this.corpusRepository = corpusRepository;
	}

	public Corpus fetchBy(String name) { return corpusRepository.findByName(name); }

	public List<Corpus> fetchAll() { return corpusRepository.findAll(); }

	public void save(Corpus corpus) { corpusRepository.save(corpus); }

	public void clearAll() {
		corpusRepository.deleteAll();
	}
}
