package de.knuff0r.bsb.domain;

import de.knuff0r.bsb.App;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/*
 * @author sebastian
 */
@Entity(name = "bsb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = true)
    private String username;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean accepted;

    @Column(nullable = false)
    private String activateKey;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    public enum Role {
        USER, ADMIN
    }

    protected User() {
        this.role = Role.USER;
        this.date = System.currentTimeMillis();
        this.activateKey = App.genMD5("" + Math.random() + System.currentTimeMillis());
    }

    public User(String email, String pwd) {
        this();
        this.email = email;
        this.password = pwd;
    }

    public User(String email, String pwd, String username) {
        this(email, pwd);
        this.username = username;
    }

    public User(String email, String pwd, boolean admin) {
        this(email, pwd);
        if(admin) {
            this.role = Role.ADMIN;
            this.active = true;
            this.accepted = true;
        }
    }

    public List<Order> getOrdersInProgress() {
        return orders.stream().filter(o -> o.getStatus() == Status.IN_PROGRESS).collect(Collectors.toList());
    }

    public List<Order> getOrdersCanceled() {
        return orders.stream().filter(o -> o.getStatus() == Status.CANCELED).collect(Collectors.toList());
    }

    public List<Order> getOrdersReady() {
        return orders.stream().filter(o -> o.getStatus() == Status.READY_FOR_COLLECTION).collect(Collectors.toList());
    }

    public List<Order> getOrdersPaid() {
        return orders.stream().filter(o -> o.getStatus() == Status.PAID_AND_DELIVERED).collect(Collectors.toList());
    }

    public List<Order> getOrdersWaitAccept() {
        return orders.stream().filter(o -> o.getStatus() == Status.WAITING_FOR_ACCEPTANCE).collect(Collectors.toList());
    }

    public List<Order> getOrdersWaitCancel() {
        return orders.stream().filter(o -> o.getStatus() == Status.WAITING_FOR_CANCEL).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getActivateKey() {
        return activateKey;
    }

    public void setActivateKey(String activateKey) {
        this.activateKey = activateKey;
    }

    public String getPassword() {
        return password;
    }
     public void setPassword(String password) {
         this.password = password;
     }

    public long getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public boolean isAdmin() {
        if (role == Role.ADMIN)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return email;
    }
}