package net.lemonsoft.DataGrab.entity;

/**
 * Created by lemonsoft on 2016/8/23.
 */
public class TaskCellEntity {
    private String fingerprint;
    private String name;
    private String publishTime;
    private String state;

    public String getFingerprint() {
        return fingerprint;
    }

    public String getName() {
        return name;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getState() {
        return state;
    }

    public TaskCellEntity(String fingerprint, String name, String publishTime, String state) {
        this.fingerprint = fingerprint;
        this.name = name;
        this.publishTime = publishTime;
        this.state = state;
    }
}
