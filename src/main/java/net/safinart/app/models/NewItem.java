package net.safinart.app.models;

public class NewItem {
    
    private String title;
    private String imageName;
    private String imageData;
    private Float lat;
    private Float lon;
    
    public static NewItem createEmpty() {
        final var item = new NewItem();
        item.setTitle("");
        item.setImageData("");
        item.setImageName("");
        item.setLat(Float.valueOf(0));
        item.setLon(Float.valueOf(0));
        return item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(final String imageData) {
        this.imageData = imageData;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(final Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(final Float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        String imageData = this.imageData != null
            ? "<imageData size:" + this.imageData.length() + ">"
            : "<imageData null>";
        return "NewItem [imageData=" + imageData + ", imageName=" + imageName + ", lat=" + lat + ", lon=" + lon
                + ", title=" + title + "]";
    }
    
}