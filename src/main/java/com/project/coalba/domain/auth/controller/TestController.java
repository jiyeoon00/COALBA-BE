package com.project.coalba.domain.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//연결 테스트용 API(인증 필요X)
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping
    public String test() {
        return "Hello World!";
    }
}
