package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.helper.FileToStringReader;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.service.CorpusService;
import org.tw.neinkeinkaffee.lda.service.LdaService;

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
        model.addAttribute("toyCorpusName", "toyCorpus");

        String corpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_small.txt");
        String stopwordString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_stop.txt");

        ldaService.clearAll();
        Corpus corpus = Corpus.fromString("hcjswb", corpusString, stopwordString);
        Lda lda = Lda.fromCorpus(corpus, 10);
        ldaService.save(lda);
        DtoLda dtolda = ldaService.fetchBy("hcjswb", 10);
        List<DtoLda> availableLdaModels = ldaService.fetchAll();

        model.addAttribute("availableModels", availableLdaModels);
        return "home";
    }
}