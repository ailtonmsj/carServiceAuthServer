package br.com.amsj.spring.authserver.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}

