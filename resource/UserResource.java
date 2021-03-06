package resource;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import dao.SpotDao;
import dao.UserDao;
import domain.Board;
import domain.CheckIn;
import domain.Coordinate;
import domain.SpotPicture;
import domain.User;
import exception.UnauthorizedException;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private User authUser;
	private UserDao dao;
	private SpotDao spotDao;
	private String authorization;
	
	public UserResource(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization){
		this.authorization = authorization;
		this.dao = new UserDao();
		this.spotDao = new SpotDao();
	}
	
	@GET
	public User loginUser(){
		this.authUser = dao.auth(authorization);
		return this.authUser;
	}
	
	@GET
	@Path("/{id}")
	public User getUser(@PathParam("id") long id){
		this.authUser = dao.auth(authorization);
		return this.dao.read(id);
	}
	
	@POST
	@Path("/getByCoordinatesFrame")
	public Collection<User> getSpotsByCoordinatesFrame(List<Coordinate> coordinates){
		this.authUser = dao.auth(authorization);
		if(coordinates.size() != 2){
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
		return this.dao.readBCoordinatesFrame(coordinates.get(0), coordinates.get(1));
	}
		
	@POST
	public User postUser(User user){
		System.out.println(user.getName());
		System.out.println(user.getClientId());
		return this.dao.create(user);
	}
	
	@PUT
	public User putUser(User user){
		this.authUser = dao.auth(authorization);
		if(user.getId() == this.authUser.getId()){
			return this.dao.update(user);
		}else{
			throw new UnauthorizedException();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteUser(@PathParam("id") long id){
		this.authUser = dao.auth(authorization);
		if(id == this.authUser.getId()){
			this.dao.delete(this.authUser);
		}else{
			throw new UnauthorizedException();
		}
	}
	
	@PUT
	@Path("/updateBoardInfo")
	public void updateBoardSize(Board board){
		this.authUser = dao.auth(authorization);
		this.dao.updateBoardNameInfo(this.authUser, board.getName(), board.getSize());
	}
	
	@PUT
	@Path("updateAvatar/{id}")
	public void addPicture(@PathParam("id") long id, SpotPicture picture) throws IOException{
		this.authUser = dao.auth(authorization);
		this.dao.updateAvatar(this.authUser, picture.getSrc());
	}
	
	@PUT
	@Path("updateBoardPicture/{id}")
	public void updateBoardPicture(@PathParam("id") long id, SpotPicture picture) throws IOException{
		this.authUser = dao.auth(authorization);
		this.dao.updateBoardPicture(this.authUser, picture.getSrc());
	}
	
	@PUT
	@Path("updateBoardLogo/{id}")
	public void updateBoardLogo(@PathParam("id") long id, SpotPicture picture) throws IOException{
		this.authUser = dao.auth(authorization);
		this.dao.updateBoardLogo(this.authUser, picture.getSrc());
	}
	
	@PUT
	@Path("addCheckIn/{id}")
	public CheckIn updateBoardLogo(@PathParam("id") long id){
		this.authUser = dao.auth(authorization);
		return this.dao.addCheckIn(this.authUser, this.spotDao.read(id));
	}
	
}
