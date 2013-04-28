package resource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import dao.CommentDao;
import dao.SpotDao;
import dao.UserDao;
import domain.Comment;
import domain.Coordinate;
import domain.Spot;
import domain.SpotPicture;
import domain.User;

@Path("/spot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpotResource {

	UserDao userdao;
	User authUser;
	SpotDao spotdao;
	CommentDao commentdao;
	
	public SpotResource(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization){
		this.userdao = new UserDao();
		this.authUser = userdao.auth(authorization);
		this.spotdao = new SpotDao();
		this.commentdao = new CommentDao();
	}
	
	@GET
	public Collection<Spot> getSpots(){
		return this.spotdao.read();
	}
	
	@POST
	@Path("/getByCoordinates")
	public Collection<Spot> getSpotsByCoordinates(Coordinate coordinates){
		return this.spotdao.readByCoordinates(coordinates);
	}
	
	@POST
	@Path("/getByCoordinatesFrame")
	public Collection<Spot> getSpotsByCoordinatesFrame(List<Coordinate> coordinates){
		if(coordinates.size() != 2){
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
		return this.spotdao.readBCoordinatesFrame(coordinates.get(0), coordinates.get(1));
	}
	
	@GET
	@Path("/{id}")
	public Spot getSpot(@PathParam("id") long id){
		Spot spot = this.spotdao.read(id);
		if(spot == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		spot.setCurrentUserAlreadyLike(this.authUser.getSpotsLike().contains(spot));
		return spot;
	}
	
	@POST
	public Spot postSpot(Spot spot){
		return this.spotdao.create(spot);
	}
	
	@PUT
	@Path("updateDescription/{id}")
	public Spot updateDescription(@PathParam("id") long id, Spot spot){
		return this.spotdao.updateDescription(this.spotdao.read(id), spot.getDescription());
	}
	
	@POST
	@Path("createComment/{id}")
	public Comment createComment(@PathParam("id") long id, Comment comment){
		comment.setUser(this.authUser);
		comment.setSpot(this.spotdao.read(id));
		return this.commentdao.create(comment);
	}
	
	@PUT//TODO @DELETE doesn't work
	@Path("deleteComment/{id}")
	public void deleteComment(@PathParam("id") long id){
		Comment comment = this.commentdao.read(id);
		if(comment.getUser().getId() == this.authUser.getId()){
			this.commentdao.delete(comment);
		}else{
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
	}
	
	@PUT
	@Path("like/{id}")
	public void like(@PathParam("id") long id){
		this.spotdao.updateLike(this.spotdao.read(id), this.authUser);
	}
	
	@PUT
	@Path("addPicture/{id}")
	public void addPicture(@PathParam("id") long id, SpotPicture picture) throws IOException{
		this.spotdao.addPicture(this.spotdao.read(id), this.authUser, picture);
	}
	
}
