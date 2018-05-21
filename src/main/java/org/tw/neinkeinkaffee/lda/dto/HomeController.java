package org.tw.neinkeinkaffee.lda.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.corpus.CorpusName;
import org.tw.neinkeinkaffee.lda.corpus.CorpusService;
import org.tw.neinkeinkaffee.lda.corpus.helper.FileToStringReader;
import org.tw.neinkeinkaffee.lda.dto.lda.LdaService;
import org.tw.neinkeinkaffee.lda.dto.lda.LdaParameterCombination;

import java.util.Arrays;
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
        String heCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_he_1827.txt");
        String heCorpusStringSmall = String.join("\n", Arrays.asList(heCorpusString.split("\n")).subList(0, 100));
        String raoCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswxb_rao_1881.txt");
        String shengCorpusString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswxb_sheng_1897.txt");
        String stopwordString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_stop.txt");
        String rareCharactersString = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_rare_characters.txt");
        String combinedCorpusString = heCorpusString + raoCorpusString + shengCorpusString;
        String stopwordAndRareCharacterString = stopwordString + rareCharactersString;

//        corpusService.clearAll();
//        Corpus heCorpus = Corpus.fromString("hcjswb_he_1827", heCorpusString, stopwordString);
//        Corpus heCorpusSmall = Corpus.fromString("hcjswb_he_1827_small", heCorpusStringSmall, stopwordString);
//        Corpus raoCorpus = Corpus.fromString("hcjswxb_rao_1881", raoCorpusString, stopwordString);
//        Corpus shengCorpus = Corpus.fromString("hcjswxb_sheng_1897", shengCorpusString, stopwordString);
//        Corpus combinedCorpus = Corpus.fromString("hcjsw_combined", combinedCorpusString, stopwordString);

        Corpus heCorpusFiltered = Corpus.fromString("hcjswb_he_1827_filtered", heCorpusString, stopwordAndRareCharacterString);
        Corpus heCorpusSmallFiltered = Corpus.fromString("hcjswb_he_1827_small_filtered", heCorpusStringSmall, stopwordAndRareCharacterString);
//        Corpus raoCorpusFiltered = Corpus.fromString("hcjswxb_rao_1881_filtered", raoCorpusString, stopwordAndRareCharacterString);
//        Corpus shengCorpusFiltered = Corpus.fromString("hcjswxb_sheng_1897_filtered", shengCorpusString, stopwordAndRareCharacterString);
//        Corpus combinedCorpusFiltered = Corpus.fromString("hcjsw_combined_filtered", combinedCorpusString, stopwordAndRareCharacterString);

//        corpusService.save(heCorpus);
//        corpusService.save(heCorpusSmall);
//        corpusService.save(raoCorpus);
//        corpusService.save(shengCorpus);
//        corpusService.save(combinedCorpus);

        corpusService.save(heCorpusFiltered);
        corpusService.save(heCorpusSmallFiltered);
//        corpusService.save(raoCorpusFiltered);
//        corpusService.save(shengCorpusFiltered);
//        corpusService.save(combinedCorpusFiltered);
        return "redirect:/";
    }

    @RequestMapping("/deleteAllCorpora")
    String deleteAllCorpora() {
        corpusService.clearAll();
        return "redirect:/";
    }

    // TODO: This should go into its own LdaController
    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/delete")
    String deleteLda(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") String timestamp,
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