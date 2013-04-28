package dao;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cloudinary.CloudImageFactory;
import domain.Coordinate;
import domain.Spot;
import domain.SpotPicture;
import domain.User;

public class SpotDao extends Dao<Spot>{
		
	public SpotDao(){

	}

	@Override
	public Spot create(Spot obj) {
		if(this.readByCoordinates(obj.getCoordinates()).size() > 0){
			throw new WebApplicationException(Response.Status.CONFLICT);
		}
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(obj);
		transaction.commit();
		session.close();
		return obj;
	}

	@Override
	public Spot read(long id) {
		Session session = Dao.FACTORY.openSession();
		Spot spot = (Spot) session.get(Spot.class, id);
		session.close();
		return spot;
	}

	@Override
	public List<Spot> read() {
		Session session = Dao.FACTORY.openSession();
		List<Spot> spots = session.createQuery("from Spot").list();
		session.close();
		return spots;
	}
	
	public List<Spot> readByCoordinates(Coordinate coordinates) {
		Session session = Dao.FACTORY.openSession();
		float MARGE = 0.001F;
		List<Spot> spots = session.createQuery("from Spot where lat between :lat1 and :lat2 and lon between :lon1 and :lon2")
				.setParameter("lat1", coordinates.getLat() - MARGE)
				.setParameter("lat2", coordinates.getLat() + MARGE)
				.setParameter("lon1", coordinates.getLon() - MARGE)
				.setParameter("lon2", coordinates.getLon() + MARGE)
				.list();
		session.close();
		return spots;
	}
	
	public List<Spot> readBCoordinatesFrame(Coordinate northWest, Coordinate southEast) {
		Session session = Dao.FACTORY.openSession();
		List<Spot> spots = session.createQuery("from Spot where lat between :lat1 and :lat2 and lon between :lon1 and :lon2")
				.setParameter("lat1", southEast.getLat())
				.setParameter("lat2", northWest.getLat())
				.setParameter("lon1", northWest.getLon())
				.setParameter("lon2", southEast.getLon())
				.list();
		session.close();
		return spots;
	}

	@Override
	public Spot update(Spot obj) {
		// TODO Auto-generated method stub
		return null;
	}
	public Spot updateDescription(Spot spot, String description){
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		spot.setDescription(description);
		session.update(spot);
		transaction.commit();
		session.close();
		return spot;
	}

	@Override
	public void delete(Spot obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateLike(Spot spot, User user){
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();	
		if(user.getSpotsLike().contains(spot)){
			user.getSpotsLike().remove(spot);
		}else{
			user.getSpotsLike().add(spot);
		}
		session.merge(user);
		transaction.commit();
		session.close();
	}
	
	public void addPicture(Spot spot, User user, SpotPicture picture) throws IOException{
		Random rnd = new Random();
		String fileName = "spot_"+spot.getId()+"_user_"+user.getId()+"_"+rnd.nextInt(100000);
		
		CloudImageFactory.savePictureByBase64(picture.getSrc(), fileName);
		
		picture.setSrc(fileName);
		Session session = Dao.FACTORY.openSession();
		Transaction transaction = session.beginTransaction();
		picture.setSpot(spot);
		picture.setUser(user);
		session.save(picture);
		transaction.commit();
		session.close();
	}

}
