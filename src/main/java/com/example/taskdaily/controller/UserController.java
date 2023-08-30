package com.example.taskdaily.controller;

import com.example.taskdaily.domain.Enums.EGender;
import com.example.taskdaily.service.user.UserService;
import com.example.taskdaily.service.user.request.UserEditRequest;
import com.example.taskdaily.service.user.request.UserSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/index")
    public ModelAndView showList(){
        ModelAndView viewList= new ModelAndView("/user/index");
        viewList.addObject("user",userService.findAll());
        return viewList;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView viewCreate = new ModelAndView("/user/create");
        viewCreate.addObject("user", new UserSaveRequest());
        viewCreate.addObject("genders", EGender.values());
        return viewCreate;
    }

    @PostMapping("/create")
    public ModelAndView created(@ModelAttribute UserSaveRequest userSaveRequest) {
        ModelAndView viewCreated = new ModelAndView("redirect:/user/index");
        userService.createUser(userSaveRequest);
        viewCreated.addObject("message", "Created");
        viewCreated.addObject("user", new UserSaveRequest());
        viewCreated.addObject("genders", EGender.values());
        return viewCreated;
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/user/index?message=Deleted";

    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        ModelAndView view = new ModelAndView("/user/edit");
        view.addObject("user", userService.showEditById(id));
        view.addObject("genders", EGender.values());
        return view;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute UserEditRequest userEditRequest,
                             @PathVariable Long id){
        ModelAndView view = new ModelAndView("user/edit");
        try{
            userService.edit(userEditRequest, id);
        }catch (Exception e){
            view.addObject("message", e.getMessage());
            view.addObject("status", e.getMessage());
            view.addObject("user", userEditRequest);
            view.addObject("genders", EGender.values());
            return view;
        }

        view.addObject("message", "Edited");
        view.addObject("user", userEditRequest);
        view.addObject("genders", EGender.values());
        return view;
    }
}
