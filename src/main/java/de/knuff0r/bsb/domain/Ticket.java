package de.knuff0r.bsb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/*
 * @author sebastian
 */
@Entity(name = "bsb_ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = true)
    private long timestamp;

    @OneToOne
    private User user;

    @Column(nullable = true)
    private String message;

    protected Ticket() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String dateAsString() {
        return new Date(timestamp).toString();
    }
}