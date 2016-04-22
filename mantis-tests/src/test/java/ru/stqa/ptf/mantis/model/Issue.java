package ru.stqa.ptf.mantis.model;

/**
 * Created by Olga on 20.04.2016.
 */
public class Issue {

    private int id;
    private String summary;
    private String status;
    private String resolution;
    private String description;
    private Project project;

    public int getId() {
        return id;
    }

    public Issue setId(int id) {
        this.id = id;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Issue setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue setDescription(String description) {
        this.description = description;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Issue setProject(Project project) {
        this.project = project;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Issue setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getResolution() {
        return resolution;
    }

    public Issue setResolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", status='" + status + '\'' +
                ", resolution='" + resolution + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
