package net.safinart.app.models;

public class Position {
    public Long lat;
    public Long lon;
    
    public static Position createEmpty() {
        var pos = new Position();
        pos.lat = Long.valueOf(0);
        pos.lon = Long.valueOf(0);
        return pos;
    }
}