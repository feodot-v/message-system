package com.mycompany.messagesystem.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.mycompany.messagesystem.entity.Message;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;
   
    public MessageServiceTest() {
    }
    @Test
    @DatabaseSetup("classpath:twoMessagesData.xml")
    @ExpectedDatabase(value = "classpath:threeMessagesData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveMessageTest(){
        Message m = new Message(3L, "Пока", "feodot", "ira", null);
        assertFalse(messageService.saveMessage(m));
        m.setToUser("tanya");
        assertTrue(messageService.saveMessage(m));
    }
    @Test
    @DatabaseSetup("classpath:threeMessagesData.xml")
    public void getInboxMessagesTest(){
        List<Message> list = new LinkedList<Message>();
        list.add(new Message(2L, "Привет", "tanya", "feodot", null));
        List<Message> result = messageService.getInboxMessages("feodot");
        assertEquals(list, result);
    }
    @Test
    @DatabaseSetup("classpath:threeMessagesData.xml")
    public void getSentMessagesTest(){
        List<Message> list = new LinkedList<Message>();
        list.add(new Message(1L, "Привет", "feodot", "tanya", null));
        list.add(new Message(3L, "Пока", "feodot", "tanya", null));
        List<Message> result = messageService.getSentMessages("feodot");
        assertEquals(list, result);
    }
    @Test
    @DatabaseSetup("classpath:threeMessagesData.xml")
    @ExpectedDatabase(value = "classpath:deleteMessageData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteMessageTest(){
      messageService.deleteMessage(3L, "sent");
    }
    @Test
    @DatabaseSetup("classpath:deleteMessageData.xml")
    @ExpectedDatabase(value = "classpath:twoMessagesData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void fullDeleteMessageTest(){
      messageService.deleteMessage(3L, "inbox");
    }
    @Test
    @DatabaseSetup("classpath:threeMessagesData.xml")
    @ExpectedDatabase(value = "classpath:deleteAllMessagesData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteAllMessagesTest(){
        messageService.deleteAllMessages("feodot");
    }
}