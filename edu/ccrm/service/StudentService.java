package edu.ccrm.service;

import edu.ccrm.domain.Student;

import java.util.*;

public class StudentService {
    private Map<String, Student> students = new HashMap<>();

    public void addStudent(Student s) {
        if (!students.containsKey(s.getId())) {
            students.put(s.getId(), s);
        }
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}
