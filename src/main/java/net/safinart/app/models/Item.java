package net.safinart.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "item")
public class Item {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name="title")
    private String title;
    
    @Column(name="image")
    private String image;
    
    @Column(name="lat")
    private Float lat;
    
    @Column(name="lon")
    private Float lon;
    
    public static Item createEmpty() {
        var item = new Item();
        var lon = Float.valueOf(0);
        var lat = Float.valueOf(0);
        item.setLat(lat);
        item.setLon(lon);
        item.setImage("");
        item.setTitle("");
        return item;
    }
    
    public static Item createFromNewItem(NewItem newItem) {
        var item = new Item();
        item.setTitle(newItem.getTitle());
        item.setImage(newItem.getImageName());
        item.setLat(newItem.getLat());
        item.setLon(newItem.getLon());
        return item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", image=" + image + ", lat=" + lat + ", lon=" + lon
            + ", title=" + title + "]";
    }
    
}