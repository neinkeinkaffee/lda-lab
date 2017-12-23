package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.helper.FileToStringReader;
import org.tw.neinkeinkaffee.lda.model.CorpusName;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;
import org.tw.neinkeinkaffee.lda.service.CorpusService;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class HomeController {
    private CorpusService corpusService;
    private LdaService ldaService;

    @Autowired
    public HomeController(final CorpusService corpusService, final LdaService ldaService) {
        this.corpusService = corpusService;
        this.ldaService = ldaService;
    }

    @RequestMapping("/")
    String home(Model model) {
        List<LdaParameterCombination> availableLdaModels = ldaService.fetchAll();
        model.addAttribute("availableModels", availableLdaModels);
        model.addAttribute("ldaParameterCombination", new LdaParameterCombination());
        List<CorpusName> availableCorpora = corpusService.fetchAll();
        model.addAttribute("availableCorpora", availableCorpora);
        return "home";
    }

    @RequestMapping("/createAllCorpora")
    String createAllCorpora() {
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
        return "redirect:/";
    }

    // TODO: This should go into its own LdaController
    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/delete")
    String deleteLda(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") Timestamp timestamp,
                     Model model) {
        ldaService.clearBy(corpusName, numberOfTopics, timestamp);
        return "redirect:/";
    }

    @RequestMapping("/deleteAll")
    String deleteAllLda(Model model) {
        ldaService.clearAll();
//        corpusService.clearAll();
        return "redirect:/";
    }

    @PostMapping("/createLda")
    public String createLda(@ModelAttribute LdaParameterCombination ldaParameterCombination) {
        ldaService.create(ldaParameterCombination);
        return "redirect:/";
    }
}