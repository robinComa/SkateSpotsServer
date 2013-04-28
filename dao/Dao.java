package dao;

import java.util.List;

import org.hibernate.SessionFactory;

import Util.HibernateUtil;

public abstract class Dao<T> {

	protected static final SessionFactory FACTORY = HibernateUtil.getSessionFactory();

	public abstract T create(T obj);
	public abstract T read(long id);
	public abstract List<T> read();
	public abstract T update(T obj);
	public abstract void delete(T obj);

}
