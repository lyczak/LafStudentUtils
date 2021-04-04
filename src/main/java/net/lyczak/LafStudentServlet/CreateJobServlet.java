package net.lyczak.LafStudentServlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.lyczak.LafStudentServlet.Jobs.Job;
import net.lyczak.LafStudentServlet.Jobs.JobResult;
import net.lyczak.LafStudentServlet.Jobs.JobType;
import org.apache.http.HttpStatus;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@WebServlet(value = "/jobs/create")
public class CreateJobServlet extends HttpServlet {
    private static final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException  {
        response.setContentType("application/json");

        response.setStatus(HttpStatus.SC_OK);
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(new JobTypesResponse()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        CreateJobRequest create;
        try {
            create = gson.fromJson(request.getReader(), CreateJobRequest.class);
        } catch (JsonIOException e) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(JobResult.error(
                    "Failed to read request input")));
            return;
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            out.println(gson.toJson(JobResult.error(
                    "Malformed request")));
            return;
        }

        UserSession session = SessionManager.getInstance()
                .getSession(create.getUserId());
        if (session == null ||
                !session.getToken()
                        .equals(create.getSessionToken())) {
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            out.println(gson.toJson(JobResult.error(
                    "No user session or invalid token")));
            return;
        }

        JobType jobType;
        try {
            jobType = JobType.valueOf(create.jobType);
        } catch (IllegalArgumentException | NullPointerException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            out.println(gson.toJson(JobResult.error(
                    "Bad jobType")));
            return;
        }

        Job job = session.addJob(jobType, create.isRepeating());

        if (job == null) {
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(JobResult.error(
                    "Failed to add job")));
            return;
        }

        response.setStatus(HttpStatus.SC_CREATED);
        out.println(JobResult.ok(job.getId()));
    }

    private class CreateJobRequest {
        private String jobType;
        private boolean repeating;
        private String userId;
        private String sessionToken;

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public boolean isRepeating() {
            return repeating;
        }

        public void setRepeating(boolean repeating) {
            this.repeating = repeating;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
        }
    }

    private class JobTypesResponse {
        private Collection<String> jobTypes;

        public JobTypesResponse() {
            jobTypes = Arrays.stream(JobType.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }

        public Collection<String> getJobTypes() {
            return jobTypes;
        }
    }
}