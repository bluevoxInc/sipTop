/**
 * 
 */
package org.bluevox.inc.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author wnorman
 *
 */
public interface Dao<T extends Object> {
	
	/**
	 *  * <p>
	 * If the passed object has a <code>setDateCreated(Date)</code> method 
	 * then call it, passing a timestamp.
	 * </p>
	 * 
	 * @param t
	 */
	void create(T t);
	
	/**
	 * Finds the requested object in the repository and returns it, 
	 * or <code>null</null> if there is no instance.
	 * 
	 * @param id ID
	 * @return requested object or null
	 */
	T get(Serializable id);
	
	/**
	 * * <p>
	 * Returns either a proxy for the requested object 
	 * (one having the right class and ID), or else the actual object
	 * if it's available without hitting the repository (e.g. in cache). 
	 * The basic idea behind this method is to allow
	 * apps establish references to the requested object 
	 * without requiring a call to the repository.
	 * </p>
	 * <p>
	 * Use this method only if you assume the instance actually exists; 
	 * i.e., non-existence throws an exception.
	 * </p>
	 * 
	 * @param id
	 * @return 
	 */
	T load(Serializable id);
	
	/**
	 * @return
	 */
	List<T> getAll();
	
	/**
	 * @param t
	 */
	void update(T t);
	
	/**
	 * @param t
	 */
	void delete(T t);
	
	/**
	 * @param id
	 */
	void deleteById(Serializable id);
	
	/**
	 * 
	 */
	void deleteAll();
	
	/**
	 * @return
	 */
	long count();
	
	/**
	 * @param id
	 * @return
	 */
	boolean exists(Serializable id);

}
