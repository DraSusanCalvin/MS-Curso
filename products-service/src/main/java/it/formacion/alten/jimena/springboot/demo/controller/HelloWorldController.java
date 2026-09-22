package it.formacion.alten.jimena.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloWorldController {

    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("/helloWorld2/{name}")
    public String helloWorld2(@PathVariable String name){
        return "Hello " + name + "!";
    }
}
