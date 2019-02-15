package br.com.amsj.spring.authserver.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.subst.Token;

@RestController
public class ResourceController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
    	
    	System.out.println("--> principal name: " + user.getName());
    	
        return user;
    }
    
    @RequestMapping("/token")
    public Token token(Token token) {
    	
    	System.out.println("--> token: " + token);
    	
        return token;
    }
}

