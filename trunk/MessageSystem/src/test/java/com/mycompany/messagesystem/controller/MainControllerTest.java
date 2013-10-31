package com.mycompany.messagesystem.controller;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainControllerTest {
    
    public MainControllerTest() {
    }
    @Test
    public void showMainPageTest(){
        MainController mainController = new MainController();
        String page = mainController.showMainPage();
        assertEquals("main", page);
    }
}