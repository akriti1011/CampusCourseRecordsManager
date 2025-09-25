package edu.ccrm.domain;

public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = null;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }

    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public String toString() {
        String gradeStr = (grade == null) ? "Not graded" : grade.toString();
        return student.getFullName() + " - " + course.getCode() + ": " + gradeStr;
    }
}
