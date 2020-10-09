package com.inomera.promotionstest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping(value = {"", "/"})
    public String test(){
        return "test";
    }
}
