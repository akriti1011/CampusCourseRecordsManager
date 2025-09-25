package edu.ccrm.service;

import edu.ccrm.domain.Course;

import java.util.*;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course c) {
        if (!courses.containsKey(c.getCode())) {
            courses.put(c.getCode(), c);
        }
    }

    public Course getCourse(String code) {
        return courses.get(code);
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }
}
