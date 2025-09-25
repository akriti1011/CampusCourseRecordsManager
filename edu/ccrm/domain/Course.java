package edu.ccrm.domain;

import edu.ccrm.domain.Semester;

public class Course {
    private String code;
    private String title;
    private int credits;
    private Semester semester;

    public Course(String code, String title, int credits, Semester semester) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.semester = semester;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Semester getSemester() { return semester; }

    @Override
    public String toString() {
        return code + ": " + title + " (" + credits + " credits, " + semester + ")";
    }
}
