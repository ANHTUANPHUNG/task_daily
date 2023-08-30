package com.example.taskdaily.controller;

import com.example.taskdaily.domain.Enums.ETaskStatus;
import com.example.taskdaily.domain.Enums.ETaskType;
import com.example.taskdaily.service.Task.request.EditRequest;
import com.example.taskdaily.service.TaskHistory.TaskHistoryService;
import com.example.taskdaily.service.TaskHistory.request.TaskEditRequest;
import com.example.taskdaily.service.TaskHistory.request.TaskSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value ="/task")
@AllArgsConstructor
public class HomeController {
    private final TaskHistoryService taskHistoryService;
    @GetMapping
    public ModelAndView showListTasks(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("/task/index");
        view.addObject("tasks", taskHistoryService.getTasks());
        view.addObject("message", message);
        view.addObject("statuses", ETaskStatus.values());
        return view;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView view = new ModelAndView("/task/create");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", ETaskType.values());
        view.addObject("statuses", ETaskStatus.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute TaskSaveRequest task) {
        ModelAndView view = new ModelAndView("redirect:/task");
        taskHistoryService.create(task);
        view.addObject("message", "Created");
        view.addObject("task", new TaskSaveRequest());
        view.addObject("taskTypes", ETaskType.values());
        view.addObject("taskStatuses", ETaskStatus.values());
        return view;
    }
    @GetMapping("/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable ETaskStatus status){
        taskHistoryService.changeStatus(id, status);
        return "redirect:/task?message=Change Success";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        taskHistoryService.deleteById(id);
        return "redirect:/task?message=Deleted";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView viewEditTitle( @PathVariable Long id){
        ModelAndView view = new ModelAndView("task/edit");
        view.addObject("task", taskHistoryService.showEditTitle(id));
        view.addObject("taskTypes", ETaskType.values());
        view.addObject("taskStatuses", ETaskStatus.values());
        return  view;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute TaskEditRequest taskEditRequest, @PathVariable Long id){
        ModelAndView view = new ModelAndView("/task/edit");
        taskHistoryService.edit(taskEditRequest,id);
        view.addObject("message", "Edited");
        view.addObject("task", taskEditRequest);
        view.addObject("taskTypes", ETaskType.values());
        view.addObject("taskStatuses", ETaskStatus.values());
        return view;
    }
}
