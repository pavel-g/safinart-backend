package net.safinart.app.models;

public class Position {
    public Float lat;
    public Float lon;
    
    public static Position createEmpty() {
        var pos = new Position();
        pos.lat = Float.valueOf(0);
        pos.lon = Float.valueOf(0);
        return pos;
    }
}