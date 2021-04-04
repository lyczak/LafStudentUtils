package net.lyczak.LafStudentServlet;

import net.lyczak.LafStudentServlet.Jobs.Job;

import java.util.Iterator;
import java.util.LinkedList;

public class UserJobSequence implements Runnable {
    private LinkedList<Job> jobs = new LinkedList<>();

    public boolean isEmpty() {
        return jobs.isEmpty();
    }

    public boolean add(Job job) {
        if (job == null) {
            return false;
        }

        jobs.add(job);
        return true;
    }

    public boolean remove(Job job) {
        return jobs.remove(job);
    }

    @Override
    public void run() {
        if (jobs.isEmpty()) {
            return;
        }

        Iterator<Job> iter = jobs.iterator();
        do  {
            Job job = iter.next();
            job.run();

            if (!job.isRepeating()) {
                iter.remove();
            }
        } while (iter.hasNext());
    }
}
