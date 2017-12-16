package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.service.LdaService;

@Controller
public class DocumentController {
    private LdaService ldaService;

    @Autowired
    public DocumentController(final LdaService ldaService) {
        this.ldaService = ldaService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/document/{document_name}")
    String listTopic(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("document_name") String documentName,
                     Model model) {
        DtoLda lda = ldaService.fetchBy(corpusName, numberOfTopics);
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("document", lda.getDocuments().get(documentName));
        return "document";
    }
}