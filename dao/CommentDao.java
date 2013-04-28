package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import domain.Comment;

public class CommentDao extends Dao<Comment>{
	
	public CommentDao(){

	}

	@Override
	public Comment create(Comment obj) {
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(obj);
		transaction.commit();
		session.close();
		return obj;
	}

	@Override
	public Comment read(long id) {
		Session session = Dao.FACTORY.openSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		session.close();
		return comment;
	}

	@Override
	public List<Comment> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment update(Comment obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Comment obj) {
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(obj);
		transaction.commit();
		session.close();
	}

}
