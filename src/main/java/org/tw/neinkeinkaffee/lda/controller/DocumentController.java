package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.service.DocumentService;

@Controller
public class DocumentController {
    private DocumentService documentService;

    @Autowired
    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/document/{document_name}")
    String listDocument(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") String timestamp,
                     final @PathVariable("document_name") String documentName,
                     Model model) {
        DtoDocument document = documentService.fetchBy(corpusName, numberOfTopics, timestamp, documentName);
        model.addAttribute("document", document);
        return "document";
    }

    @RequestMapping("/api/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/document/{document_name}")
    @ResponseBody
    DtoDocument listDocumentJson(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") String timestamp,
                     final @PathVariable("document_name") String documentName,
                     Model model) {
        DtoDocument document = documentService.fetchBy(corpusName, numberOfTopics, timestamp, documentName);
        model.addAttribute("document", document);
        return document;
    }
}