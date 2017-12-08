/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.sbs;

/**
 *
 * @author maradona
 */
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public abstract class AbstractFacade<T> implements Serializable {
 
 
 private Class<T> entityClass;
 
 protected final static Logger logger = Logger.getLogger(AbstractFacade.class); 
    
    //private static final String PERSISTENCE_UNIT_NAME =  "Eclipselink_JPA" ;
 
  

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;

    }

    public EntityManagerFactory getEntityManagerFactory() {
        logger.info("get the entityManagerFactory");
        //Map<String, String> properties = new HashMap<String, String>();
        //properties.put("javax.persistence.jdbc.user", "root");
        //properties.put("javax.persistence.jdbc.pasword", "root");
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("jdbc:postgresql://localhost:5432/sometestdb", properties);
       // EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
       logger.info("getting the damn factory");
       EntityManagerFactory emf = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
       logger.info("got the damn factory");
        
        logger.info("we got the manager factory");


        return emf;
    }

    public  EntityManager getEntityManager() {
        logger.info("get the entity manager");

        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        logger.info("we got the factory");
        return em;

    }

    public void create(T entity) {
        logger.info("create the entity manager");

        EntityManager em = null;
        try {
            logger.info("persist the created entity");
            em = getEntityManager();
            logger.info("got the entity manager");
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            logger.info("begin the transaction");
            em.persist(entity);
            logger.info("persist the transaction");
            tx.commit();
            logger.info("commit the transaction");
        }
        
        catch(Exception e ) {
        if(em.getTransaction().isActive());
        em.getTransaction().rollback();
        }
        
        finally {
            if(em !=null)
                em.close();
            //if(em.getTransaction().isActive());
            //em.getTransaction().rollback();

        }
    }

    public String edit(T entity) {

        logger.info("edit the entity");
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            return "Create";
        }
        finally {
            if(em.getTransaction().isActive());
            em.getTransaction().rollback();
        }
    }

    public void remove(T entity) {
        logger.info("remove the entity");
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        logger.info("find the said entity");
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        logger.info("find all entities");
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public List<T> findEntries(String squery, HashMap<String, Object> params) {
        logger.info("find the given enties");
        EntityManager em = getEntityManager();
        Query query = em.createQuery(squery);
        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }
        return query.getResultList();
    }

    public List<T> findRange(int[] range) {
        List<String> list = new ArrayList<String>();
        logger.info("finding info using range");
        EntityManager em = null;
        em = getEntityManager();
        logger.info("find the range of entities");
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        logger.info("select");
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        logger.info("return our result in the range");
        return q.getResultList();
    }

    public int count() {
        logger.info("count the entities");
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}