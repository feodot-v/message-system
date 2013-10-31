package com.mycompany.messagesystem.dao.jpa;

import com.mycompany.messagesystem.dao.MessageDAO;
import com.mycompany.messagesystem.entity.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

//@Repository
//@Qualifier("jpa")
public class JPAMessageDAO implements MessageDAO{
    @PersistenceContext
    private EntityManager em;
    private final String MESSAGES_FROM_USER = 
            "select m from m Message where m.fromUser=:username";
    private final String MESSAGES_TO_USER = 
            "select m from m Message where m.toUser=:username";

    @Override
    public void saveMessage(Message message) {
        em.persist(message);
    }

    @Override
    public void deleteMessage(Message message) {
        em.remove(message);
    }

    @Override
    public List<Message> getInboxtMessages(String userName) {
        Query q = em.createQuery(MESSAGES_TO_USER);
        return (List<Message>)q.setParameter("username", userName).getResultList();
    }

    @Override
    public List<Message> getSenttMessages(String userName) {
        Query q = em.createQuery(MESSAGES_FROM_USER);
        return (List<Message>)q.setParameter("username", userName).getResultList();
    }

    @Override
    public Message getMessage(Long messageID) {
        return em.find(Message.class, messageID);
    }
   
    

}
