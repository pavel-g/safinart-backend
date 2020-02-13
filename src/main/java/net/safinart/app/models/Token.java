package net.safinart.app.models;

public class Token {
    public String token;
    
    public static Token createFromSession(Session s) {
        var t = new Token();
        t.token = s.getId().toString();
        return t;
    }
}