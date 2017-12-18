package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tw.neinkeinkaffee.lda.helper.FileToStringReader;
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
        this.ldaParameterCombinationService = ldaParameterCombinationService;
    }

    @RequestMapping("/")
    String home(Model model) {
        model.addAttribute("toyCorpusName", "toyCorpus");

        String corpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_nano.txt");
        String stopwordString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_stop.txt");
//        ldaService.clearAll();
//        Corpus corpus = Corpus.fromString("hcjswb", corpusString, stopwordString);
//        Corpus corpus1 = Corpus.fromString("hcjswb1", corpusString, stopwordString);
//        Corpus corpus2 = Corpus.fromString("hcjswb2", corpusString, stopwordString);
//        Lda lda = Lda.fromCorpus(corpus, 3);
//        Lda lda1 = Lda.fromCorpus(corpus1, 2);
//        Lda lda2 = Lda.fromCorpus(corpus2, 1);
//        ldaService.save(lda);
//        ldaService.save(lda1);
//        ldaService.save(lda2);
        List<LdaParameterCombination> availableLdaModels = ldaParameterCombinationService.fetchAll();
        model.addAttribute("availableModels", availableLdaModels);
        List<Corpus> availableCorpora = corpusService.fetchAll();
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
    String eraseEverything(Model model) {
        ldaService.clearAll();
        corpusService.clearAll();
        return "redirect:/";
    }
}