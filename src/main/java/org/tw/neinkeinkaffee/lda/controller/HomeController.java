package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tw.neinkeinkaffee.lda.helper.FileToStringReader;
import org.tw.neinkeinkaffee.lda.model.CorpusName;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.service.CorpusService;
import org.tw.neinkeinkaffee.lda.service.LdaParameterCombinationService;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.util.List;

@Controller
public class HomeController {
    private CorpusService corpusService;
    private LdaService ldaService;
    private LdaParameterCombinationService ldaParameterCombinationService;

    @Autowired
    public HomeController(final CorpusService corpusService, final LdaService ldaService, final LdaParameterCombinationService ldaParameterCombinationService) {
        this.corpusService = corpusService;
        this.ldaService = ldaService;
    }

    @RequestMapping("/")
    String home(Model model) {
        model.addAttribute("toyCorpusName", "toyCorpus");

        String nanoCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_nano.txt");
        String smallCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_small.txt");
        String first100CorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_first100.txt");
        String first500CorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_first500.txt");
        String fullCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb.txt");
        String stopwordString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_stop.txt");
        corpusService.clearAll();
        Corpus nanoCorpus = Corpus.fromString("hcjswb_nano", nanoCorpusString, stopwordString);
        Corpus smallCorpus = Corpus.fromString("hcjswb_small", smallCorpusString, stopwordString);
        Corpus first100Corpus = Corpus.fromString("hcjswb_first100", first100CorpusString, stopwordString);
        Corpus first500Corpus = Corpus.fromString("hcjswb_first500", first500CorpusString, stopwordString);
        Corpus fullCorpus = Corpus.fromString("hcjswb", fullCorpusString, stopwordString);
        corpusService.save(nanoCorpus);
        corpusService.save(smallCorpus);
        corpusService.save(first100Corpus);
        corpusService.save(first500Corpus);
        corpusService.save(fullCorpus);
//        Lda lda = Lda.fromCorpus(corpus, 3);
//        Lda lda1 = Lda.fromCorpus(corpus1, 2);
//        Lda lda2 = Lda.fromCorpus(corpus2, 1);
//        ldaService.save(lda);
//        ldaService.save(lda1);
//        ldaService.save(lda2);
        List<LdaParameterCombination> availableLdaModels = ldaService.fetchAll();
        model.addAttribute("availableModels", availableLdaModels);
        List<CorpusName> availableCorpora = corpusService.fetchAll();
        model.addAttribute("availableCorpora", availableCorpora);
        return "home";
    }

    // TODO: This should go into its own LdaController
    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/delete")
    String deleteLda(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     Model model) {
        ldaService.clearBy(corpusName, numberOfTopics);
        return "redirect:/";
    }

    @RequestMapping("/delete")
    String deleteAllLda(Model model) {
        ldaService.clearAll();
        corpusService.clearAll();
        return "redirect:/";
    }
}