package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import javax.persistence.ManyToMany;

@Entity
public class Listing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double price;
    private double rating;

    @ManyToOne(cascade = {})
    private Owner owner;
    
    @ManyToOne(cascade = {})
    private Location location;
    
    @ManyToMany(cascade = {}, fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<Tag>();

    public Listing() {
    }

    public Listing(String title, String description, double price, double rating, Owner owner) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Listing [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
                + ", rating=" + rating + ", owner=" + owner + ", location=" + location + "]";
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
