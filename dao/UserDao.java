package dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cloudinary.CloudImageFactory;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.core.util.Base64;

import domain.Board;
import domain.CheckIn;
import domain.Coordinate;
import domain.Spot;
import domain.SpotPicture;
import domain.User;
import exception.UnauthorizedException;

public class UserDao extends Dao<User>{
	
	public UserDao(){

	}
	
	public User auth(String authorizationBASE64){
		Session session = Dao.FACTORY.openSession();
		try{		         
			String clientId = new String(Base64.decode(authorizationBASE64));
			User u = (User) session.createCriteria(User.class).add(Restrictions.eq("clientId", clientId) ).uniqueResult();
			if(u != null){
				session.close();
				return u;
			}else{
				throw new UnauthorizedException();
			}
		}catch (Exception ex){
			session.close();
			throw new UnauthorizedException();
		}
	}

	@Override
	public User create(User obj) {
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.save(obj);
			transaction.commit();
		}catch(org.hibernate.exception.ConstraintViolationException ex){
			throw new ConflictException();
		}finally{
			session.close();
		}
		return obj;
	}
	
	@Override
	public List<User> read() {
		Session session = Dao.FACTORY.openSession();
		List<User> users = session.createQuery("from User").list();
		session.close();
		return users;
	}

	@Override
	public User read(long id) {
		Session session = Dao.FACTORY.openSession();
		User user = (User) session.get(User.class, id);
		session.close();
		return user;
	}
	
	public List<User> readBCoordinatesFrame(Coordinate northWest, Coordinate southEast) {
		Session session = Dao.FACTORY.openSession();
		List<User> users = session.createQuery("from User where lat between :lat1 and :lat2 and lon between :lon1 and :lon2")
				.setParameter("lat1", southEast.getLat())
				.setParameter("lat2", northWest.getLat())
				.setParameter("lon1", northWest.getLon())
				.setParameter("lon2", southEast.getLon())
				.list();
		session.close();
		return users;
	}

	@Override
	public User update(User obj) {
		return null;
	}

	@Override
	public void delete(User obj) {
		
	}
	
	public void updateBoardNameInfo(User user, String name, float size){
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		if(user.getBoard() == null){
			user.setBoard(new Board());
		}
		user.getBoard().setName(name);
		user.getBoard().setSize(size);
		session.update(user);
		transaction.commit();
		session.close();
	}
	
	public void updateAvatar(User user, String src) throws IOException{
		String fileName = user.getAvatar();
		if(fileName == null){
			Random rnd = new Random();
			fileName = "user_"+user.getId()+"_avatar_"+rnd.nextInt(100000);
			Session session = Dao.FACTORY.openSession();
			Transaction transaction = session.beginTransaction();
			user.setAvatar(fileName);
			session.update(user);
			transaction.commit();
			session.close();
		}
		
		CloudImageFactory.savePictureByBase64(src, fileName);		
	}
	
	public void updateBoardPicture(User user, String src) throws IOException{
		if(user.getBoard() == null){
			user.setBoard(new Board());
		}
		String fileName = user.getBoard().getPicture();
		if(fileName == null){
			Random rnd = new Random();
			fileName = "user_"+user.getId()+"_board_"+rnd.nextInt(100000);
			Session session = Dao.FACTORY.openSession();
			Transaction transaction = session.beginTransaction();
			user.getBoard().setPicture(fileName);
			session.update(user);
			transaction.commit();
			session.close();
		}
		
		CloudImageFactory.savePictureByBase64(src, fileName);		
	}
	
	public void updateBoardLogo(User user, String src) throws IOException{
		if(user.getBoard() == null){
			user.setBoard(new Board());
		}
		String fileName = user.getBoard().getLogo();
		if(fileName == null){
			Random rnd = new Random();
			fileName = "user_"+user.getId()+"_board_logo_"+rnd.nextInt(100000);
			Session session = Dao.FACTORY.openSession();
			Transaction transaction = session.beginTransaction();
			user.getBoard().setLogo(fileName);
			session.update(user);
			transaction.commit();
			session.close();
		}
		
		CloudImageFactory.savePictureByBase64(src, fileName);		
	}
	public CheckIn addCheckIn(User user, Spot spot){
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		CheckIn checkin = new CheckIn();
		checkin.setUser(user);
		checkin.setSpot(spot);
		Date currentDate = new Date();
		checkin.setDate(currentDate.getTime());
		session.save(checkin);
		transaction.commit();
		session.close();
		return checkin;
	}

}
