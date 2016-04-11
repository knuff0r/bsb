package de.knuff0r.bsb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/*
 * @author sebastian
 */
@Entity(name="bsb_notifification")
public abstract class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	

	@Column(nullable=false)
	protected boolean read=false;

	@Column(nullable=false)
	private long timestamp_in;

	@OneToOne
	private User user;

	protected Notification(){
	}
	

	
	public Notification(User user) {
		super();
		this.timestamp_in = System.currentTimeMillis();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}
	
	public Date getTimestamp_in(){
		return new Date(this.timestamp_in);
	}



	public boolean isRead() {
		return read;
	}



	public void setRead(boolean read) {
		this.read = read;
	}

	
	
	
}