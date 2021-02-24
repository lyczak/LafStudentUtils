package net.lyczak.LafStudentUtils.Models;

public class MoodleEventAction {
    private String name;
    private String url;
    private int itemcount;
    private boolean actionable;
    private boolean showitemcount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

    public boolean isActionable() {
        return actionable;
    }

    public void setActionable(boolean actionable) {
        this.actionable = actionable;
    }

    public boolean isShowitemcount() {
        return showitemcount;
    }

    public void setShowitemcount(boolean showitemcount) {
        this.showitemcount = showitemcount;
    }
}
