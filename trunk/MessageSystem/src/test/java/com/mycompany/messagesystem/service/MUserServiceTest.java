package com.mycompany.messagesystem.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.mycompany.messagesystem.entity.MUser;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class MUserServiceTest {
    @Autowired
    private MUserService userService;
    private MUser u;
    private final Long id = 3L;
    
    public MUserServiceTest() {
        u = new MUser();
    }
    @Before
    public void init(){
        u.setId(null);
        u.setFirstname("Ира");
        u.setLastname("Ворона");
        u.setPassword("ira2009");
        u.setUserName("ira"); 
    }
    
    @Test
    @DatabaseSetup("classpath:threeUsersData.xml")
    public void getUserTest(){
        u.setId(id);
        MUser result = userService.getUser(id);
        assertEquals(u, result);
    }
    @Test
    @DatabaseSetup("classpath:twoUsersData.xml")
    @ExpectedDatabase(value = "classpath:threeUsersData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveUserTest(){
        assertTrue(userService.saveUser(u));
        assertFalse(userService.saveUser(u));
    }
    @Test
    @DatabaseSetup("classpath:threeUsersData.xml")
    @ExpectedDatabase(value = "classpath:updateUserData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateUserTest(){
        u.setId(id);
        u.setPassword("ira2013");
        assertTrue(userService.updateUser(u));
        u.setUserName("tanya");
        assertFalse(userService.updateUser(u));
        u.setUserName("irulya");
        assertTrue(userService.updateUser(u));
    }
    @Test
    @DatabaseSetup("classpath:threeUsersData.xml")
    public void getUsersListTest(){
        List<MUser> list = new LinkedList<MUser>();
        list.add(new MUser(1L,"Богдан","Ворона","bohdan","feodot"));
        list.add(new MUser(2L, "Таня", "Ворона", "tanya83", "tanya"));
        u.setId(id);
        list.add(u);
        List<MUser> result = userService.geAllUsers();
        assertEquals(list, result);
    }
    @Test
    @DatabaseSetup("classpath:threeUsersData.xml")
    public void getUsersNamesTest(){
       List<String> list = new LinkedList<String>();
       list.add("feodot");
       list.add("tanya");
       list.add("ira");
       List<String> result = userService.getNamesList();
       assertEquals(list, result);
    }
    @Test
    @DatabaseSetup("classpath:threeUsersData.xml")
    @ExpectedDatabase(value = "classpath:twoUsersData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteUserTest(){
      userService.deleteUser(id);
    }
}