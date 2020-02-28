package net.safinart.app.services;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.safinart.app.exceptions.NotAuthorized;
import net.safinart.app.exceptions.SessionDead;
import net.safinart.app.exceptions.WrongPassword;
import net.safinart.app.exceptions.WrongUsername;
import net.safinart.app.models.Session;

@Service
public class AuthService {
    
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${app.auth.name}")
    private String configUser;

    @Value("${app.auth.password}")
    private String configPassword;
    
    @Value("${app.auth.session_lifetime}")
    private Long configSessionLifetime = Long.valueOf(21600); // 6h

    private List<Session> sessions = new ArrayList<Session>();

    public Session checkLogin(String name, String password) throws WrongUsername, WrongPassword {
        if (!name.equals(this.configUser)) {
            this.logger.error("Не верное имя пользователя {}", name);
            throw new WrongUsername("Не верное имя пользователя");
        }
        if (!password.equals(this.configPassword)) {
            this.logger.error("Не верный пароль {} для пользователя {}", password, name);
            throw new WrongPassword("Не верный пароль");
        }
        var existsSession = this.findSessionByNameAndPassword(name, password);
        if (existsSession.isEmpty()) {
            var newSession = this.createNewSession(name, password);
            this.logger.info(
                "Создана новая сессия для {} (id={})",
                name,
                newSession.getId().toString()
            );
            return newSession;
        }
        if (!this.isSessionDead(existsSession.get())) {
            this.logger.info(
                "Использование существующей сессии для пользователя {} (id={})",
                name,
                existsSession.get().getId().toString()
            );
            return existsSession.get();
        }
        this.sessions.remove(existsSession.get());
        var newSession = this.createNewSession(name, password);
        this.logger.info(
            "Создана новая сессия для {} (id={})",
            name,
            newSession.getId().toString()
        );
        return newSession;
    }
    
    public Session checkToken(UUID sessionId) throws NotAuthorized, SessionDead {
        var existsSession = this.findSessionById(sessionId);
        if (existsSession.isEmpty()) {
            throw new NotAuthorized("Не верный токен");
        }
        this.checkSessionLifetime(existsSession.get());
        return existsSession.get();
    }

    protected Optional<Session> findSessionById(UUID id) {
        var s = this.sessions.stream()
            .filter( (session) -> session.getId().toString().equals( id.toString() ) )
            .findFirst();
        return s;
    }
    
    protected Optional<Session> findSessionByNameAndPassword(String name, String password) {
        var s = this.sessions.stream()
            .filter(session -> ((session.getName().equals(name)) && (session.getPassword().equals(password))))
            .findFirst();
        return s;
    }
    
    protected void checkSessionLifetime(Session s) throws SessionDead {
        if (this.isSessionDead(s)) {
            this.sessions.remove(s);
            throw new SessionDead("Сессия истекла");
        }
    }
    
    protected boolean isSessionDead(Session s) {
        var now = ZonedDateTime.now(ZoneOffset.UTC);
        var duration = Duration.between(s.getStartedDate(), now);
        return (duration.getSeconds() > this.configSessionLifetime);
    }
    
    protected Session createNewSession(String name, String password) {
        var s = new Session(name, password);
        this.sessions.add(s);
        return s;
    }
    
}