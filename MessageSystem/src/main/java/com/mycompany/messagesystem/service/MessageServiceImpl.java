package com.mycompany.messagesystem.service;

import com.mycompany.messagesystem.dao.MessageDAO;
import com.mycompany.messagesystem.dao.UserDAO;
import com.mycompany.messagesystem.entity.Message;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MessageServiceImpl implements MessageService{
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    @Qualifier("hibernate")
    private UserDAO userDAO;
    @Autowired
    @Qualifier("hibernate")
    private MessageDAO messageDAO;
    
    @Override
    public boolean saveMessage(Message message) {
        if(userDAO.getNamesList().contains(message.getToUser())){
            messageDAO.saveMessage(message);
            logger.info("save "+message);
            return true;  
        }
        logger.info("saveMessage() return false");
        return false;    
    }
    @Transactional(readOnly = true)
    @Override
    public List<Message> getInboxMessages(String userName) {
        logger.info("getInboxMessages");
        return messageDAO.getInboxtMessages(userName);
    }
    @Transactional(readOnly = true)
    @Override
    public List<Message> getSentMessages(String userName) {
        logger.info("getSentMessages");
        return messageDAO.getSenttMessages(userName);
    }

    @Override
    public void deleteMessage(Long messageID, String messageType) {
        Message message = messageDAO.getMessage(messageID);
        logger.info("delete "+message);
        if(messageType.equals("inbox")){
            message.setToUser(null);
        }
        else {
            message.setFromUser(null);
        }
        if(message.getToUser()==null&&message.getFromUser()==null){
            messageDAO.deleteMessage(message);
        }
    }
    
    @Override
    public void deleteAllMessages(String userName){
        List<Message> messages = getInboxMessages(userName);
        for(Message message: messages){
            deleteMessage(message.getId(), "inbox");
        }
        messages.clear();
        messages.addAll(getSentMessages(userName));
        for(Message message: messages){
            deleteMessage(message.getId(), "sent");
        }
        logger.info("delete all messages "+userName);
    }
}