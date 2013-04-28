package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name="COMMENT")
public class Comment {

	@Id
    @GeneratedValue
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPOT_ID", nullable = false)
	private Spot spot;
	
	@Column(name="date", nullable = false)
	private long date;
	
	@Column(name="comment", nullable = false, length=255)
	private String comment;
	
	public Comment(){
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@XmlTransient
	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
