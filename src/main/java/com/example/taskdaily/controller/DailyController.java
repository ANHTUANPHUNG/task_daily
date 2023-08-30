package com.example.taskdaily.controller;

import com.example.taskdaily.domain.Enums.EGender;
import com.example.taskdaily.domain.TaskHistory;
import com.example.taskdaily.service.Task.TaskService;
import com.example.taskdaily.service.Task.request.EditRequest;
import com.example.taskdaily.service.Task.request.SaveRequest;
import com.example.taskdaily.service.TaskHistory.request.TaskEditRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/daily")
@AllArgsConstructor
public class DailyController {
    private final TaskService taskService;
    @GetMapping("/list")
    public ModelAndView showListTasks(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("daily/list");
        view.addObject("dailies", taskService.findAll());
        view.addObject("message", message);
        return view;
    }
    @GetMapping("/create")
    public ModelAndView showCreateDaily() {
        ModelAndView viewCreate = new ModelAndView("/daily/create");
        viewCreate.addObject("daily", new SaveRequest());
        return viewCreate;
    }

    @PostMapping("/create")
    public String CreateDaily(@ModelAttribute SaveRequest daily) {
        taskService.createTask(daily);
        return "redirect:/daily/list?message=Created";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        taskService.deleteById(id);
        return "redirect:/daily/list?message=Deleted";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView viewEditTitle( @PathVariable Long id){
        ModelAndView view = new ModelAndView("daily/edit");
        view.addObject("daily", taskService.showEditTitle(id));
        return  view;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute EditRequest editRequest, @PathVariable Long id){
        ModelAndView view = new ModelAndView("redirect:/daily/list?message=Edited");
        taskService.editDaily(editRequest,id);
//        view.addObject("message", "Edited");
        view.addObject("daily", editRequest);
        return view;
    }
}
