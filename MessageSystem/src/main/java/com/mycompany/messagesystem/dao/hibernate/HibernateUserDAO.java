package com.mycompany.messagesystem.dao.hibernate;

import com.mycompany.messagesystem.dao.UserDAO;
import com.mycompany.messagesystem.entity.MUser;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("hibernate")
public class HibernateUserDAO extends AbstractHibernateDAO implements UserDAO{

    @Autowired
    public HibernateUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    
    @Override
    @CacheEvict(value = "lists", allEntries = true)
    public void saveUser(MUser user) {
        Session session = super.getCurentSession();
        session.save(user);
        session.flush();
    }

    @Override
    @CacheEvict(value = "lists", allEntries = true)
    public void deleteUser(MUser user) {
        super.getCurentSession().delete(user);
    }

    @Override
    @CacheEvict(value = "lists", allEntries = true)
    public void updateUser(MUser user) {
        super.getCurentSession().merge(user);
    }

    @Override
    @Cacheable(value = "lists", key = "#root.methodName")
    public List<MUser> getAllUsers() {
        List<MUser> users = super.getCurentSession().createCriteria(MUser.class).list();
        return users;
    }

    @Override
    @Cacheable(value = "lists", key = "#root.methodName")
    public List<String> getNamesList() {
        List namesList = super.getCurentSession().
                createQuery("select userName from MUser").list();
        return namesList;
    }

    @Override
    public MUser getUser(Long userID) {
        return (MUser) super.getCurentSession().load(MUser.class, userID);
    }
    
    

}
