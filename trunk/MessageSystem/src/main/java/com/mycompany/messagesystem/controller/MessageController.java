package com.mycompany.messagesystem.controller;

import com.mycompany.messagesystem.entity.Message;
import com.mycompany.messagesystem.service.MessageService;
import com.mycompany.messagesystem.service.MUserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.*;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final Logger loger = LogManager.getLogger(MessageController.class);
    private MessageService messageService;
    private MUserService userService;

    @Autowired
    public MessageController(MessageService messageService, MUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showMessageForm(Model model) {
        Message message = new Message();
        List<String> usersNames = userService.getNamesList();
        model.addAttribute("message",message);
        model.addAttribute("usersNames",usersNames);
        loger.info("showMessageForm() return message/form");
        return "message/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createMessage(@Valid Message message, BindingResult bindingResult, Model model) {
        loger.info(String.format("after validation %s number of errors: %d", message.toString(), bindingResult.getErrorCount()));
        if (bindingResult.hasErrors()) {
            loger.info("createMessage() return message form with bindingResult");
            return "message/form";
        }
        try {
            if (!messageService.saveMessage(message)) {
                String errMessage = String.format("Пользователь с именем %s не найден", message.getToUser());
                model.addAttribute("errMessage", errMessage);
                loger.info("return message/form with errMessage");
                return "message/form";
            }
        } catch (Throwable e) {
            loger.error(e.getMessage(), e);
            return "message/error";
        }
        loger.info("createMessage() return message/success");
        return "message/success";
    }

    @RequestMapping(value = "/inbox/{userName}")
    public String showInboxMessages(@PathVariable String userName, Model model) {
        List<Message> messages = messageService.getInboxMessages(userName);
        model.addAttribute("messages", messages);
        model.addAttribute("userName", userName);
        loger.info("return message/inbox");
        return "message/inbox";
    }

    @RequestMapping(value = "/sent/{userName}")
    public String showSentMessages(@PathVariable String userName, Model model) {
        List<Message> messages = messageService.getSentMessages(userName);
        model.addAttribute("messages", messages);
        model.addAttribute("userName", userName);
        loger.info("return message/sent");
        return "message/sent";
    }

    @RequestMapping(value = "/delete")
    public String deleteMessage(@RequestParam("messageID") String messageID,
                                @RequestParam("messageType")String messageType){
        try{
        messageService.deleteMessage(new Long(messageID), messageType);
        }catch(Throwable e){
            loger.error(e.getMessage(), e);
            return "user/error";}
        loger.info("deleteMessage() redirect:/message/"+messageType);
        return "redirect:/message/"+messageType;
    }
}
