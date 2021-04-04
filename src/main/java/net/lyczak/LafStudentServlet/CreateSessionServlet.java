package net.lyczak.LafStudentServlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpStatus;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

@WebServlet("/sessions/create")
public class CreateSessionServlet extends HttpServlet {
    private static final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Database.getInstance().initDb();
        } catch (SQLException e) {
            throw new ServletException("Database initialization failed", e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (!request.getContentType().equals("application/json")) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            out.println(gson.toJson(Result.error(
                    "Bad content-type")));
            return;
        }

        CreateSessionRequest create;
        try {
            create = gson.fromJson(request.getReader(), CreateSessionRequest.class);
        } catch (JsonIOException e) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(Result.error(
                    "Failed to read request input")));
            return;
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            out.println(gson.toJson(Result.error(
                    "Malformed request")));
            return;
        }

        String valErrs = null;
        if(create == null) {
            valErrs = "No request object present";
        } else {
            valErrs = create.getValidationErrors();
        }
        if (valErrs != null) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            out.println(gson.toJson(Result.error(
                    valErrs)));
            return;
        }

        if (SessionManager.getInstance().getSession(create.userId) != null) {
            response.setStatus(HttpStatus.SC_CONFLICT);
            out.println(gson.toJson(Result.error(
                    "Session already exists")));
            return;
        }

        LssUser user = Database.getInstance()
                .findUserByUserId(create.userId);
        if (user == null || !user.getCasUsername().equals(create.casUsername)) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            out.println(gson.toJson(Result.error(
                    "Bad userId or casUsername")));
            return;
        }

        RemoteWebDriver webDriver;
        try {
            URL driverUrl = new URL(user.getWebDriverUrl());
            webDriver = new RemoteWebDriver(driverUrl, new DesiredCapabilities());

        } catch (UnreachableBrowserException e) {
            response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
            out.println(gson.toJson(Result.error(
                    "Failed to connect to webdriver")));
            return;
        } catch (MalformedURLException e) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(Result.error(
                    "Failed to parse webdriver url")));
            return;
        }

        UserSession session = new UserSession(user, webDriver,
                new SimpleCredentialProvider(create.casUsername, create.casPassword));

        SessionManager.getInstance().putSession(session);

        response.setStatus(HttpStatus.SC_CREATED);
        out.println(gson.toJson(Result.ok(new CreateSessionResponse(
                session.getUserId(), session.getToken()))));
    }

    private class CreateSessionRequest {
        private String userId;
        private String casUsername;
        private String casPassword;

        public String getValidationErrors() {
            StringBuilder sb = new StringBuilder();

            if (userId == null || userId.isEmpty()) {
                sb.append("userId must not be null or empty, ");
            }
            if (casUsername == null || casUsername.isEmpty()) {
                sb.append("casUsername must not be null or empty, ");
            }
            if (casPassword == null || casPassword.isEmpty()) {
                sb.append("casPassword must not be null or empty, ");
            }

            return sb.length() == 0 ? null : sb.toString();
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCasUsername() {
            return casUsername;
        }

        public void setCasUsername(String casUsername) {
            this.casUsername = casUsername;
        }

        public String getCasPassword() {
            return casPassword;
        }

        public void setCasPassword(String casPassword) {
            this.casPassword = casPassword;
        }
    }

    private class CreateSessionResponse {
        private String userId;
        private String token;

        public CreateSessionResponse(String userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
