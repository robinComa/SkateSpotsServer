package domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name="USER")
public class User {

	@Id
    @GeneratedValue
	private long id;
	
	@Column(name="name", nullable = false, length=20)
	private String name;
	
	@Column(name="avatar", length=255)
	private String avatar;
	
	@Column(name="client_id", nullable = false, unique = true, length=255)
	@XmlElement(name = "clientId")
	private String clientId;
	
	@Column(name="email", nullable = false, unique = true, length=255)
	@XmlTransient
	private String email;
	
	@Column(name="social")
	@XmlTransient
	private String social;
	
	private Coordinate coordinates;
	
	private Board board;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<CheckIn> checksin;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Comment> comments;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<SpotPicture> spotPictures;
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="USER_SPOT_LIKE",joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="SPOT_ID")})
	private Set<Spot> spotsLike;
	
	public User(){

	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@XmlTransient
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	@Embedded
	public Coordinate getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
	
	@Embedded
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Set<CheckIn> getChecksin() {
		return checksin;
	}
	public void setChecksin(Set<CheckIn> checksin) {
		this.checksin = checksin;
	}

	@XmlTransient
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@XmlTransient
	public Set<Spot> getSpotsLike() {
		return spotsLike;
	}

	public void setSpotsLike(Set<Spot> spotsLike) {
		this.spotsLike = spotsLike;
	}

	@XmlTransient
	public Set<SpotPicture> getSpotPictures() {
		return spotPictures;
	}

	public void setSpotPictures(Set<SpotPicture> spotPictures) {
		this.spotPictures = spotPictures;
	}
	
}
