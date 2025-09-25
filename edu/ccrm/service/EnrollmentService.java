package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.*;

public class EnrollmentService {
    private Map<String, List<Enrollment>> enrollmentsByStudent = new HashMap<>();
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public boolean enroll(String studentId, String courseCode) {
        Student s = studentService.getStudent(studentId);
        Course c = courseService.getCourse(courseCode);
        if (s == null || c == null) return false;

        List<Enrollment> list = enrollmentsByStudent.getOrDefault(studentId, new ArrayList<>());

        // Check if already enrolled
        for (Enrollment e : list) {
            if (e.getCourse().getCode().equals(courseCode)) return false;
        }
        Enrollment e = new Enrollment(s, c);
        list.add(e);
        enrollmentsByStudent.put(studentId, list);
        return true;
    }

    public boolean recordGrade(String studentId, String courseCode, Grade grade) {
        List<Enrollment> list = enrollmentsByStudent.get(studentId);
        if (list == null) return false;
        for (Enrollment e : list) {
            if (e.getCourse().getCode().equals(courseCode)) {
                e.setGrade(grade);
                return true;
            }
        }
        return false;
    }

    public String getTranscript(String studentId) {
        List<Enrollment> list = enrollmentsByStudent.get(studentId);
        if (list == null) return "No enrollments found.";
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(studentService.getStudent(studentId).getFullName()).append(":\n");
        double totalPoints = 0;
        int totalCredits = 0;
        for (Enrollment e : list) {
            sb.append(e.toString()).append("\n");
            Grade g = e.getGrade();
            if (g != null) {
                totalPoints += g.getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        double gpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;
        sb.append(String.format("GPA: %.2f", gpa));
        return sb.toString();
    }

    public Collection<Enrollment> getAllEnrollments() {
        List<Enrollment> all = new ArrayList<>();
        for (List<Enrollment> l : enrollmentsByStudent.values()) {
            all.addAll(l);
        }
        return all;
    }
}
