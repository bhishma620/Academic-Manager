package com.bhishma.ams.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://127.0.0.1:5500")
public class AdminController {

    @GetMapping("")
    public String test() {
        return "HELLO FROM ADMIN";
    }


}
