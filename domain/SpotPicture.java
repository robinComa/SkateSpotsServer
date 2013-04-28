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
@Table(name="SPOT_PICTURE")
public class SpotPicture {

	@Id
    @GeneratedValue
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPOT_ID", nullable = false)
	private Spot spot;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name="src", nullable = false, length=255)
	private String src;
	
	public SpotPicture(){
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@XmlTransient
	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	@XmlTransient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
