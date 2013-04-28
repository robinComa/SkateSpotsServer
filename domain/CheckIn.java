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
@Table(name="CHECKIN")
public class CheckIn {

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
	
	public CheckIn(){
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	@XmlTransient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

	public Spot getSpot() {
		Spot spotDTO = new Spot();
		spotDTO.setCoordinates(this.spot.getCoordinates());
		spotDTO.setId(this.spot.getId());
		spotDTO.setName(this.spot.getName());
		return spotDTO;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}	
	
}
