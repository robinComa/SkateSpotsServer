package domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Embeddable
public class Coordinate {

    @Column(name = "lat", nullable = false)
	private float lat;

    @Column(name = "lon", nullable = false)
    private float lon;
	
	public Coordinate(){
		
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}
	
}
