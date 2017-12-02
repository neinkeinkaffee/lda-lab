package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    private LdaService ldaService;

    @Autowired
    public HomeController(final LdaService ldaService) {
        this.ldaService = ldaService;
    }

    @RequestMapping("/")
    String home(Model model) {
        UUID toyUUID = UUID.fromString("c2cef191-e025-43bb-9021-6413335bbf5d");
        model.addAttribute("toyUUID", toyUUID);
        List<Lda> availableLdaModels = ldaService.fetchAll();
        model.addAttribute("availableModels", availableLdaModels);
        return "home";
    }
}