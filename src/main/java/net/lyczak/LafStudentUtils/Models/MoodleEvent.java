package net.lyczak.LafStudentUtils.Models;

public class MoodleEvent {
    private String name;
    private String description;
    private String eventtype;
    private long timestart;
    private long timeduration;
    private long timesort;
    private long timemodified;
    private boolean canedit;
    private boolean candelete;
    private String deleteurl;
    private String editurl;
    private String viewurl;
    private String formattedtime;
    private boolean isactionevent;
    private boolean iscourseevent;
    private boolean iscategoryevent;
    private String url;
    private MoodleCourse course;
    private MoodleEventAction action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public long getTimestart() {
        return timestart;
    }

    public void setTimestart(long timestart) {
        this.timestart = timestart;
    }

    public long getTimeduration() {
        return timeduration;
    }

    public void setTimeduration(long timeduration) {
        this.timeduration = timeduration;
    }

    public long getTimesort() {
        return timesort;
    }

    public void setTimesort(long timesort) {
        this.timesort = timesort;
    }

    public long getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(long timemodified) {
        this.timemodified = timemodified;
    }

    public boolean isCanedit() {
        return canedit;
    }

    public void setCanedit(boolean canedit) {
        this.canedit = canedit;
    }

    public boolean isCandelete() {
        return candelete;
    }

    public void setCandelete(boolean candelete) {
        this.candelete = candelete;
    }

    public String getDeleteurl() {
        return deleteurl;
    }

    public void setDeleteurl(String deleteurl) {
        this.deleteurl = deleteurl;
    }

    public String getEditurl() {
        return editurl;
    }

    public void setEditurl(String editurl) {
        this.editurl = editurl;
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl;
    }

    public String getFormattedtime() {
        return formattedtime;
    }

    public void setFormattedtime(String formattedtime) {
        this.formattedtime = formattedtime;
    }

    public boolean isIsactionevent() {
        return isactionevent;
    }

    public void setIsactionevent(boolean isactionevent) {
        this.isactionevent = isactionevent;
    }

    public boolean isIscourseevent() {
        return iscourseevent;
    }

    public void setIscourseevent(boolean iscourseevent) {
        this.iscourseevent = iscourseevent;
    }

    public boolean isIscategoryevent() {
        return iscategoryevent;
    }

    public void setIscategoryevent(boolean iscategoryevent) {
        this.iscategoryevent = iscategoryevent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MoodleCourse getCourse() {
        return course;
    }

    public void setCourse(MoodleCourse course) {
        this.course = course;
    }

    public MoodleEventAction getAction() {
        return action;
    }

    public void setAction(MoodleEventAction action) {
        this.action = action;
    }
}
