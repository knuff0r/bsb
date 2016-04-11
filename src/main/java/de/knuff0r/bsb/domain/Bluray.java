package de.knuff0r.bsb.domain;

import javax.persistence.*;
import javax.persistence.ElementCollection;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
 * @author sebastian
 */
@Entity(name = "bsb_bluray")
public class Bluray implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private boolean d3;

    @Column(nullable = true)
    private Integer rating;

    @Column(nullable = true, unique = true)
    private String imdb;

    @ElementCollection(targetClass = Language.class)
    @Enumerated(EnumType.STRING)
    @Column
    private Set<Language> languages = new HashSet<>();

    @Column(nullable = false)
    private PriceRange priceRange;

    @Column(nullable = false)
    private boolean original;

    @Column(nullable = false)
    private boolean ripped;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Bluray(String title) {
        this();
        this.title = title;
    }

    protected Bluray() {
        this.priceRange = PriceRange.SINGLE;
    }

    public enum Language {
        GERMAN, ENGLISH, FRENCH;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isOriginal() {
        return original;
    }

    public boolean isRipped() {
        return ripped;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public boolean isD3() {
        return d3;
    }

    public void setD3(boolean d3) {
        this.d3 = d3;
    }

    public boolean getOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public boolean getRipped() {
        return ripped;
    }

    public void setRipped(boolean ripped) {
        this.ripped = ripped;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}