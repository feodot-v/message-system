package com.mycompany.messagesystem.dao.hibernate;

import com.mycompany.messagesystem.dao.MessageDAO;
import com.mycompany.messagesystem.entity.Message;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("hibernate")
public class HibernateMessageDAO extends AbstractHibernateDAO implements MessageDAO {

    @Autowired
    public HibernateMessageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void saveMessage(Message message) {
        super.getCurentSession().save(message);
    }

    @Override
    public void deleteMessage(Message message) {
        super.getCurentSession().delete(message);
    }

    @Override
    public List<Message> getInboxtMessages(String user) {
        List<Message> messages = super.getCurentSession().createCriteria(Message.class)
                .add(Restrictions.eq("toUser", user)).list();
        return messages;
    }

    @Override
    public List<Message> getSenttMessages(String user) {
        List<Message> messages = super.getCurentSession().createCriteria(Message.class)
                .add(Restrictions.eq("fromUser", user)).list();
        return messages;
    }

    @Override
    public Message getMessage(Long messageID) {
        return (Message) super.getCurentSession().load(Message.class, messageID);
    }
    
}
