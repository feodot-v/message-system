package com.mycompany.messagesystem.controller;

import com.mycompany.messagesystem.entity.MUser;
import com.mycompany.messagesystem.service.MUserService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final Logger logger = LogManager.getLogger(UserController.class);
    private MUserService userService;

    @Autowired
    public UserController(MUserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showUserForm(Model model) {
        MUser user = new MUser();
        model.addAttribute(user);
        logger.info("showUserForm");
        return "user/form";
    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String showUpdateForm(@RequestParam("userID") String userID, Model model){
        MUser user = userService.getUser(new Long(userID));
        model.addAttribute(user);
        logger.info("showUpdateForm");
        return "user/form";
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute("MUser") MUser user, BindingResult bindingResult, Model model){
        logger.info(String.format("createUser() after validation user %s number of errors - %d",
                user.getUserName(),bindingResult.getErrorCount()));
        if(bindingResult.hasErrors()){
            logger.info("createUser() return user/form with bindingResult");
            return "user/form";
        }
        try{
            if(!userService.saveUser(user)){
                String errMessage = String.format("Пользователь с именем %s уже существует", user.getUserName());
                model.addAttribute("errMessage",errMessage);
                logger.info("createUser() return user/form with errMessage");
                return "user/form";
            }
            }catch(Throwable e){
                logger.error(e.getMessage(), e);
                return "user/error";
        }
        logger.info("createUser() return user/success");
        return "user/success";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #user.userName == authentication.name")
    public String updateUser(@Valid MUser user, BindingResult bindingResult, Model model){
        logger.info(String.format("udateUser() after validation user %s number of errors - %d",
                user.getUserName(),bindingResult.getErrorCount()));
        if(bindingResult.hasErrors()){
            logger.info("createUser() return user/form with bindingResult");
            return "user/form";
        }
        try{
            if(!userService.updateUser(user)){
                String errMessage = String.format("Пользователь с именем %s уже существует", user.getUserName());
                model.addAttribute("errMessage",errMessage);
                logger.info("updateUser() return user/form with errMessage");
                return "user/form";
            }
            }catch(Throwable e){
                logger.error(e.getMessage(), e);
                return "user/error";
        }
        logger.info("updateUser() return user/success");
        return "user/success";
    }
    @RequestMapping(value = "/list")
    public String showUserList(Model model){
        logger.info("showUserList");
        List<MUser> users = userService.geAllUsers();
        model.addAttribute("users", users);
        if(model.containsAttribute("users"))logger.info("showUserList put attribute 'users' to the model");
        return "user/list";
    }
    @RequestMapping(value = "/delete")
    public String deleteUser(@RequestParam("userID")String UserID){
        try{
            userService.deleteUser(new Long(UserID));
        }catch(Throwable e){
            logger.error(e.getMessage(), e);
            return "user/error";
        }
        logger.info("deleteUser() redirect:/user/list");
        return "redirect:/user/list";
    }
}
