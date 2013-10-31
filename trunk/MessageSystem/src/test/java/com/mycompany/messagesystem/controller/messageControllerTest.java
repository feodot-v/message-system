package com.mycompany.messagesystem.controller;

import com.mycompany.messagesystem.entity.Message;
import com.mycompany.messagesystem.service.MUserService;
import com.mycompany.messagesystem.service.MessageService;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

public class messageControllerTest {

    private MessageController messageController;
    private MessageService messageService;
    private MUserService userService;
    private ExtendedModelMap model;
    private String page;
    List<Message> messages;

    @Before
    public void init() {
        userService = mock(MUserService.class);
        messageService = mock(MessageService.class);
        messageController = new MessageController(messageService, userService);
        model = new ExtendedModelMap();
    }
    @Test
    public void showMessageFormTest(){
        List<String> list = new LinkedList<String>();
        list.add("feodot");
        list.add("tanya");
        when(userService.getNamesList()).thenReturn(list);
        page = messageController.showMessageForm(model);
        assertEquals("message/form", page);
        assertEquals(new Message(),model.get("message") );
        assertTrue(model.containsValue(list));
    }
    @Test
    public void createMessageTest(){
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getErrorCount()).thenReturn(1);
        when(bindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        Message message = new Message(1L, "title", "feodot", "tanya", null);
        page = messageController.createMessage(message, bindingResult, model);
        assertEquals("message/form", page);
        
        when(bindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        when(messageService.saveMessage(message)).thenReturn(Boolean.FALSE);
        String errMessage = String.format("Пользователь с именем %s не найден", message.getToUser());
        page = messageController.createMessage(message, bindingResult, model);
        assertEquals("message/form", page);
        assertEquals(errMessage, model.get("errMessage"));
        
        when(messageService.saveMessage(message)).thenThrow(RuntimeException.class);
        page = messageController.createMessage(message, bindingResult, model);
        assertEquals("message/error", page);
        
        Message m1 = new Message();
        when(messageService.saveMessage(m1)).thenReturn(Boolean.TRUE);
        page = messageController.createMessage(m1, bindingResult, model);
        assertEquals("message/success", page);
    }
    @Test
    public void showInboxMessagesTest(){
        messages = new LinkedList<Message>();
        messages.add(new Message());
        messages.add(new Message());
        when(messageService.getInboxMessages("feodot")).thenReturn(messages);
        page = messageController.showInboxMessages("feodot", model);
        assertEquals("message/inbox", page);
        assertTrue(model.containsValue(messages));
    }
    @Test
    public void showSentMessagesTest(){
        when(messageService.getSentMessages("feodot")).thenReturn(messages);
        page = messageController.showSentMessages("feodot", model);
        assertEquals("message/sent", page);
        assertTrue(model.containsValue(messages));
    }
    @Test
    public void deleteMessageTest(){
        doThrow(RuntimeException.class).when(messageService).deleteMessage(1L, "inbox");
        page = messageController.deleteMessage("1", "inbox");
        assertEquals("user/error", page);
        page = messageController.deleteMessage("2", "inbox");
        assertEquals("redirect:/message/inbox", page);
        verify(messageService).deleteMessage(2L, "inbox");
    }
}
