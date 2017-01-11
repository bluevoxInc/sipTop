/**
 * 
 */
package org.bluevox.inc.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.ReflectionUtils;

import org.bluevox.inc.dao.Dao;

/**
 * @author wnorman
 *
 *
* Don't put @Transactional here. It's not that it's inherently wrong
* --indeed it would allow us to avoid some pass-though service bean methods
* --but using @Transactional causes Spring to create proxies, 
* and recipe 10.3 assumes that it has direct access to the DAOs. 
* I don't think we're doing direct DAO injects into controllers anywhere. [WLW]
*/

public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {

	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType)getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	private String getDomainClassName() { return getDomainClass().getName(); }
	
	
	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#create(java.lang.Object)
	 */
	public void create(T t) {
		// If there's a setDateCreated() method, set the date.
		Method method = ReflectionUtils.findMethod(
				getDomainClass(), "setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());	
			} catch (Exception e) {
				//Ignore; simply abort setDate() attempt.
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#load(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession()
				.createQuery("from " + getDomainClassName())
				.list();
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#update(java.lang.Object)
	 */
	public void update(T t) {
		getSession().update(t);
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#delete(java.lang.Object)
	 */
	public void delete(T t) {
		getSession().delete(t);
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#deleteById(java.io.Serializable)
	 */
	public void deleteById(Serializable id) {
		delete(load(id));
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#deleteAll()
	 */
	public void deleteAll() {
		getSession()
			.createQuery("delete " + getDomainClassName())
			.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#count()
	 */
	public long count() {
		return (Long) getSession()
				.createQuery("select count(*) from " + getDomainClassName())
				.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.bluevox.inc.dao.Dao#exists(java.io.Serializable)
	 */
	public boolean exists(Serializable id) {
		return (get(id) != null);
	}

}
