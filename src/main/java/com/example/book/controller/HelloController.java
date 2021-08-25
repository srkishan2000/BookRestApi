package com.example.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(path = "/hello/{callerName}", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello(@PathVariable("callerName") String callerName) {
        log.info("Invoked - /hello/{}", callerName);
        return new ResponseEntity<String>(String.format("Hello, %s!", callerName), HttpStatus.OK);
    }
}
