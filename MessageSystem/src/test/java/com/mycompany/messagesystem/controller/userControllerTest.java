package com.mycompany.messagesystem.controller;

import com.mycompany.messagesystem.entity.MUser;
import com.mycompany.messagesystem.service.MUserService;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

public class userControllerTest {
    private UserController userController;
    private MUserService userService;
    private ExtendedModelMap model;
    private String page;
    
    public userControllerTest() {
    }
    @Before
    public void init(){
        userService = mock(MUserService.class);
        userController = new UserController(userService);
        model = new ExtendedModelMap();
    }
    @Test
    public void showUserFormTest(){
        page = userController.showUserForm(model);
        assertEquals(page, "user/form");
        assertEquals(model.get("MUser"), new MUser());
    }
    @Test
    public void showUpdateFormTest(){
      MUser user = new MUser(1L, "Богдан", "Ворона", "bohdan", "feodot");
      when(userService.getUser(1L)).thenReturn(user);
      page = userController.showUpdateForm("1", model);
      assertEquals(page, "user/form");
      assertEquals(model.get("MUser"), user);
    }
    @Test
    public void createUserTest(){
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getErrorCount()).thenReturn(1);
        when(bindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        MUser user = new MUser(1L, "Богдан", "Ворона", "bohdan", "feodot");
        page = userController.createUser(user, bindingResult, model);
        assertEquals(page, "user/form");
        
        when(bindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        when(userService.saveUser(user)).thenReturn(Boolean.FALSE);
        String errMessage = String.format("Пользователь с именем %s уже существует", user.getUserName());
        page = userController.createUser(user, bindingResult, model);
        assertEquals(page, "user/form");
        assertEquals(model.get("errMessage"), errMessage);
        
        when(userService.saveUser(user)).thenThrow(new RuntimeException("my runtimeException"));
        page = userController.createUser(user, bindingResult, model);
        assertEquals(page, "user/error");
        
        MUser user1 = new MUser();
        when(userService.saveUser(user1)).thenReturn(Boolean.TRUE);
        page = userController.createUser(user1, bindingResult, model);
        assertEquals(page, "user/success");
    }
    @Test
    public void updateUserTest(){
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getErrorCount()).thenReturn(1);
        when(bindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        MUser user = new MUser(1L, "Богдан", "Ворона", "bohdan", "feodot");
        page = userController.updateUser(user, bindingResult, model);
        assertEquals(page, "user/form");
        
        when(bindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        when(userService.updateUser(user)).thenReturn(Boolean.FALSE);
        String errMessage = String.format("Пользователь с именем %s уже существует", user.getUserName());
        page = userController.updateUser(user, bindingResult, model);
        assertEquals(page, "user/form");
        assertEquals(model.get("errMessage"), errMessage);
        
        when(userService.updateUser(user)).thenThrow(new RuntimeException("my RuntimeException"));
        page = userController.updateUser(user, bindingResult, model);
        assertEquals(page, "user/error");
        
        MUser user1 = new MUser();
        when(userService.updateUser(user1)).thenReturn(Boolean.TRUE);
        page = userController.updateUser(user1, bindingResult, model);
        assertEquals(page, "user/success");
    }
    @Test
    public void showUserListTest(){
        List<MUser> list = new LinkedList<MUser>();
        list.add(new MUser());
        list.add(new MUser());
        when(userService.geAllUsers()).thenReturn(list);
        page = userController.showUserList(model);
        assertEquals(page, "user/list");
        assertTrue(model.containsValue(list));
    }
    @Test
    public void deleteUserTest(){
        doThrow(RuntimeException.class).when(userService).deleteUser(1L);
        page = userController.deleteUser("1");
        assertEquals(page, "user/error");
        page = userController.deleteUser("2");
        assertEquals(page, "redirect:/user/list");
        verify(userService).deleteUser(2L);
    }
}