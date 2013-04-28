package domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Embeddable
public class Board {

    @Column(name = "RolenameID")
	private String name;
    
    @Column(name = "logo", length=255)
	private String logo;
    
    @Column(name = "size")
	private float size;
    
    @Column(name = "picture", length=255)
	private String picture;
	
	public Board(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
