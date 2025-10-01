package com.example.amigo_de_patas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class Ping {

    @GetMapping
    public String ping() {
        return "Hello";
    }

    // ROTA PARA FAZER COM QUE A APLICAÇÃO RENDER NÃO HIBERNE
}
