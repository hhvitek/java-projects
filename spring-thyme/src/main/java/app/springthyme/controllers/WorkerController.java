package app.springthyme.controllers;

import app.springthyme.model.ITaskService;
import app.springthyme.model.WorkerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WorkerController {

    @Autowired
    private ITaskService task;

    @GetMapping("/")
    public String homePage(WorkerForm m) {
        return "worker";
    }

    @PostMapping("/start_worker")
    public String startWorker(@Valid WorkerForm m, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            StatusBar.setStatusBarText(model, "Error", "Fill in the form");
            return "worker";
        }

        if (task.isRunning()) {
            StatusBar.setStatusBarText(model, "Warn", "The task has already been running.");
            return "worker";
        } else {
            task.setDelaySeconds(m.getSeconds());
            task.setMaxIterations(m.getIterations());
            task.startTask();
        }

        return "worker";
    }

    @GetMapping("/stop_worker")
    public String stopWorker(Model model) {
        if (!task.isRunning()) {
            StatusBar.setStatusBarText(model, "Warn", "The task is already running.");
            return "redirect:/";
        } else {
            task.stopTask();
            return "redirect:/";
        }
    }

}
