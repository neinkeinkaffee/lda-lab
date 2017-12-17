package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.helper.ToyDataProvider;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.model.lda.LdaCounters;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.repository.CountersRepository;
import org.tw.neinkeinkaffee.lda.repository.LdaDocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LdaService {
    private CountersRepository countersRepository;
    private LdaDocumentRepository documentRepository;
    private ToyDataProvider toyDataProvider;

    @Autowired
    public LdaService(final CountersRepository countersRepository, final LdaDocumentRepository documentRepository, final ToyDataProvider provider) {
        this.countersRepository = countersRepository;
        this.documentRepository = documentRepository;
        this.toyDataProvider = provider;
    }

    public DtoLda fetchBy(String corpusName, int numberOfTopics) {
        if (corpusName.equals("toyCorpus")) {
            return toyDataProvider.produceToyLda("toyCorpus");
        }
        LdaCounters counters = countersRepository.findByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        List<LdaDocument> documents = documentRepository.findByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        Lda lda = Lda.fromCountersAndDocuments(counters, documents, corpusName, numberOfTopics);
        return lda.getDtoLda();
    }

    public List<DtoLda> fetchAll() {
        return countersRepository.findAll().stream()
            .map(counter -> fetchBy(counter.getCorpusName(), counter.getNumberOfTopics()))
            .collect(Collectors.toList());
    }

    public void save(Lda lda) {
        countersRepository.save(lda.getCounters());
        // TODO: Maybe a composite key will remove the need for storing corpusName and numberOfTopics in each document for retrieval
        for (LdaDocument document : lda.getDocuments().values()) {
            document.setCorpusName(lda.getCorpusName());
            document.setNumberOfTopics(lda.getNumberOfTopics());
            documentRepository.save(document);
        }
    }

    public void clearAll() {
        countersRepository.deleteAll();
        documentRepository.deleteAll();
    }
}
