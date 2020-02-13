package net.safinart.app.models;

public class Images {
	public String orig;
    public String min;
    
    public static Images createEmpty() {
        var images = new Images();
        images.orig = "";
        images.min = "";
        return images;
    }
}