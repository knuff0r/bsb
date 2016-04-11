package de.knuff0r.bsb.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;


/*
 * @author sebastian
 */
@Entity(name = "bsb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private long timestamp_in;

    @Column(nullable = false)
    @Min(1)
    private float price;

    @OneToOne
    private Bluray bluray;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private boolean prem = true;

    @Column(nullable = false)
    @Min(1)
    private int num = 1;


    public Order() {
        this.status = Status.WAITING_FOR_ACCEPTANCE;
        this.timestamp_in = System.currentTimeMillis();
    }

    public Date timestampAsDate(){
        return new Date(timestamp_in);
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getTimestamp_in() {
        return timestamp_in;
    }

    public Bluray getBluray() {
        return bluray;
    }

    public void setBluray(Bluray bluray) {
        this.bluray = bluray;
    }

    public boolean isPrem() {
        return prem;
    }

    public void setPrem(boolean prem) {
        this.prem = prem;
    }

    public int getNum() {
        return num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNum(int num) {
        this.num = num;
    }
}