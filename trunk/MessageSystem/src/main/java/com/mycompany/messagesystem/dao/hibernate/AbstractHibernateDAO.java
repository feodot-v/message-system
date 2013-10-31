package com.mycompany.messagesystem.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractHibernateDAO {
    private SessionFactory sessionFactory;

    protected AbstractHibernateDAO(SessionFactory sessionFactory){
       this.sessionFactory = sessionFactory; 
    }
    
    protected Session getCurentSession(){
        return sessionFactory.getCurrentSession();
    }
}
