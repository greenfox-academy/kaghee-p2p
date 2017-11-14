package com.greenfox.kaghee.chatapp.controllers;

import com.greenfox.kaghee.chatapp.models.User;
import com.greenfox.kaghee.chatapp.repos.LogRepo;
import com.greenfox.kaghee.chatapp.repos.UserRepo;
import com.greenfox.kaghee.chatapp.service.RequestHandler;
import com.greenfox.kaghee.chatapp.service.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatAppController {
    @Autowired
    LogRepo logRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RequestHandler requestHandler;

    @Autowired
    UserHandler userHandler;

    @RequestMapping(value="/")
    public String home(HttpServletRequest request, Model model) {
        requestHandler.printLog(request);
        if (userHandler.getCurrentUser() == null) {
            return "enter";
        } else {
            model.addAttribute("currentUser", userHandler.getCurrentUser());
            return "home";
        }
    }

    @GetMapping("/enter")
    public String enter() {
        return "enter";
    }

    @GetMapping("/enter/adduser")
    public String addUser(HttpServletRequest request, @RequestParam(value = "userName") String username, Model model) {
        String warning = "";
        model.addAttribute("name", username);
        if (username.equals("")) {
            warning = "The username field is empty!";
            model.addAttribute("noUser", warning);
            requestHandler.printError(request);
            return "enter";
        } else if (userRepo.findUserByUsername(username) != null) {
            warning = "This username already exists in the database.";
            model.addAttribute("noUser", warning);
            requestHandler.printError(request);
            return "enter";
        } else {
            userHandler.saveUser(username);
            requestHandler.printLog(request);
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/update")
    public String updateUser(@PathVariable(value = "id") Long id, @RequestParam(value = "userName") String username,
                             HttpServletRequest request, Model model) {
        requestHandler.printLog(request);
        userHandler.getCurrentUser().setUsername(username);
        userRepo.save(userHandler.getCurrentUser());
        model.addAttribute("currentUser", userHandler.getCurrentUser());
        return "home";
    }
}
