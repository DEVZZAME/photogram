package com.cos.photogramstart.web;


import org.springframework.web.bind.annotation.GetMapping;

public class Controller {
    @GetMapping("/")
    public String home() {
        return "auth/signin";
    }
}
