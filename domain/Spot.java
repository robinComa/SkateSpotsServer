package domain;

import java.util.HashSet;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name="SPOT")
public class Spot {
	
	@Id
    @GeneratedValue
	private long id;
	
	private Coordinate coordinates;

	@Column(name="name", nullable = false, length=20)
	private String name;

	@Column(name="description", nullable = false, length=255)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "spot")
	private Set<SpotPicture> pictures;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "spot")
	private Set<Comment> comments;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "spot")
	private Set<CheckIn> checksin;
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name="USER_SPOT_LIKE",joinColumns={@JoinColumn(name="SPOT_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private Set<User> usersLike;
	
	@Transient
	private boolean alreadyLike;

	public Spot(){
		this.usersLike = new HashSet<User>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Embedded
	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SpotPicture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<SpotPicture> pictures) {
		this.pictures = pictures;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@XmlTransient
	public Set<CheckIn> getChecksin() {
		return checksin;
	}

	public void setChecksin(Set<CheckIn> checksin) {
		this.checksin = checksin;
	}
	
	@XmlTransient
	public Set<User> getUsersLike(){
		return this.usersLike;
	}

	public void setUsersLike(Set<User> usersLike) {
		this.usersLike = usersLike;
	}
	
	@XmlElement(name = "nbLikes")
	public int getNbLikes(){
		return this.usersLike.size();
	}
	
	@XmlElement(name = "alreadyLike")
	public boolean getCurrentUserAlreadyLike(){
		return this.alreadyLike;
	}
	
	public void setCurrentUserAlreadyLike(boolean like){
		this.alreadyLike = like;
	}
	
	public boolean equals(Object obj){
		if ( this == obj ) return true;
		if ( !(obj instanceof Spot) ) return false;
		return this.getId() == ((Spot) obj).getId();
	}
	
	public int hashCode() {
	    return (int) this.id;
	  }
		
}
