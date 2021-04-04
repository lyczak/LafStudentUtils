package net.lyczak.LafStudentServlet;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String, UserSession> sessions = new HashMap<>();

    private static SessionManager instance;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if(instance == null)
            instance = new SessionManager();

        return instance;
    }

    public void putSession(UserSession session) {
        if (session == null) {
            return;
        }

        sessions.put(session.getUserId(), session);
    }

    public UserSession getSession(String userId) {
        return sessions.get(userId);
    }
}
