package app.springthyme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class StatusBar {

    public static void setStatusBarText(Model model, String status, String message) {
        model.addAttribute("status_status", status);
        model.addAttribute("status_text", message);
    }

}
