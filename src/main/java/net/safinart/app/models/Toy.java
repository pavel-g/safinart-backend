package net.safinart.app.models;

public class Toy {
    
    public Long id;
    public String title;
    public Images images;
    public Position pos;
    
    public static Toy createEmpty() {
        var toy = new Toy();
        toy.id = Long.valueOf(0);
        toy.title = "";
        toy.images = Images.createEmpty();
        toy.pos = Position.createEmpty();
        return toy;
    }
    
    public static Toy createFromItem(Item item) {
        var toy = Toy.createEmpty();
        toy.id = item.getId();
        toy.images.orig = item.getImage();
        toy.title = item.getTitle();
        return toy;
    }
    
}