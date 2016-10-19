package com.u1wan.core.database;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.u1wan.core.utils.StopWatch;

/**
 * @author bfc
 */
public class HibernateAccess {

    private final Logger logger = LoggerFactory.getLogger(HibernateAccess.class);

    SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public <T> List<T> find(HibernateQuery hibernateQuery, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = hibernateQuery.createQuery(currentSession(), params);
            return (List<T>) query.list();
        } finally {
            logger.debug("find, hibernateQuery={}, params={}, elapsedTime={}", hibernateQuery, params, watch.elapsedTime());
        }
    }

    public <T> List<T> find(String hql, Object... params) {
        return find(new HibernateQuery(hql, null, null), params);
    }

    public <T> List<T> find(String hql, Integer firstResult, Integer maxResults, Object... params) {
        return find(new HibernateQuery(hql, firstResult, maxResults), params);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(DetachedCriteria detachedCriteria, Integer firstResult, Integer maxResults) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
            if (firstResult != null) criteria.setFirstResult(firstResult);
            if (maxResults != null) criteria.setMaxResults(maxResults);
            return criteria.list();
        } finally {
            logger.debug("find, criteria={}, firstResult={}, maxResults={}, elapsedTime={}", String.valueOf(detachedCriteria), firstResult, maxResults, watch.elapsedTime());
        }
    }

    public <T> List<T> find(DetachedCriteria detachedCriteria) {
        return find(detachedCriteria, null, null);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(DetachedCriteria detachedCriteria) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
            return (T) criteria.uniqueResult();
        } finally {
            logger.debug("findUniqueResult, criteria={}, elapsedTime={}", String.valueOf(detachedCriteria), watch.elapsedTime());
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(String hql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = currentSession().createQuery(hql);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
            return (T) query.uniqueResult();
        } finally {
            logger.debug("findUniqueResult, hql={}, params={}, elapsedTime={}", hql, params, watch.elapsedTime());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Serializable id) {
        StopWatch watch = new StopWatch();
        try {
            return (T) currentSession().get(entityClass, id);
        } finally {
            logger.debug("get, entityClass={}, id={}, elapsedTime={}", entityClass.getName(), id, watch.elapsedTime());
        }
    }

    public void persist(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().persist(entity);
        } finally {
            logger.debug("persist, entityClass={}, value={}, elapsedTime={}", new Object[]{entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime()});
        }
    }
    
    public void save(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().save(entity);
        } finally {
            logger.debug("persist, entityClass={}, value={}, elapsedTime={}", new Object[]{entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime()});
        }
    }

    public void update(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().update(entity);
        } finally {
            logger.debug("update, entityClass={}, value={}, elapsedTime={}", new Object[]{entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime()});
        }
    }

    public void delete(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().delete(entity);
        } finally {
            logger.debug("delete, entityClass={}, value={}, elapsedTime={}", entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime());
        }
    }

    public int execute(String hql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = new HibernateQuery(hql, null, null).createQuery(currentSession(), params);
            return query.executeUpdate();
        } finally {
            logger.debug("execute, hql={}, params={}, elapsedTime={}", hql, params, watch.elapsedTime());
        }
    }

    public Session currentSession() {
        //return sessionFactory.getCurrentSession();
    	return sessionFactory.openSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
}