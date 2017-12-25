package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.service.LdaService;
import org.tw.neinkeinkaffee.lda.service.WordService;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class WordController {
    private WordService wordService;

    @Autowired
    public WordController(final WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/word/{word_lemma}")
    String listWord(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") Instant timestamp,
                     final @PathVariable("word_lemma") String wordLemma,
                     Model model) {
        ContentWord word = wordService.fetchBy(corpusName, numberOfTopics, timestamp, wordLemma);
//        model.addAttribute("corpusName", corpusName);
//        model.addAttribute("numberOfTopics", numberOfTopics);
//        model.addAttribute("timestamp", timestamp);
        model.addAttribute("word", word);
        return "word";
    }

    @RequestMapping("/api/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/word/{word_lemma}")
    @ResponseBody
    ContentWord listWordJson(final @PathVariable("corpus_name") String corpusName,
                        final @PathVariable("number_of_topics") int numberOfTopics,
                        final @PathVariable("timestamp") Instant timestamp,
                        final @PathVariable("word_lemma") String word_lemma,
                        Model model) {
        ContentWord word = wordService.fetchBy(corpusName, numberOfTopics, timestamp, word_lemma);

        return word;
    }
}