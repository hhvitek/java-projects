package app.springthyme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    @GetMapping("/refresh")
    public String refresh() {
        return "redirect:/";
    }

}
