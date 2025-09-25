package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileIO {

    public static void importData(String filename, StudentService ss, CourseService cs, EnrollmentService es) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            return;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 1) continue;
                String type = parts[0];
                switch(type) {
                    case "S":
                        if (parts.length >= 4) {
                            String id = parts[1];
                            String name = parts[2];
                            String email = parts[3];
                            ss.addStudent(new Student(id, name, email));
                        }
                        break;
                    case "C":
                        if (parts.length >=5) {
                            String code = parts[1];
                            String title = parts[2];
                            int credits = Integer.parseInt(parts[3]);
                            Semester sem = Semester.valueOf(parts[4].toUpperCase());
                            cs.addCourse(new Course(code, title, credits, sem));
                        }
                        break;
                    case "E":
                        if (parts.length >=4) {
                            String sid = parts[1];
                            String ccode = parts[2];
                            es.enroll(sid, ccode);
                            if (parts.length >=5) {
                                Grade grade = Grade.valueOf(parts[4].toUpperCase());
                                es.recordGrade(sid, ccode, grade);
                            }
                        }
                        break;
                }
            }
            System.out.println("Import completed.");
        } catch(Exception e) {
            System.out.println("Import error: "+e.getMessage());
        }
    }

    public static void exportData(String filename, StudentService ss, CourseService cs, EnrollmentService es) {
        Path path = Paths.get(filename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Student s : ss.getAllStudents()) {
                bw.write("S," + s.getId() + "," + s.getFullName() + "," + s.getEmail());
                bw.newLine();
            }
            for (Course c : cs.getAllCourses()) {
                bw.write("C," + c.getCode() + "," + c.getTitle() + "," + c.getCredits() + "," + c.getSemester());
                bw.newLine();
            }
            for (Enrollment e : es.getAllEnrollments()) {
                String gradeStr = e.getGrade() == null ? "" : e.getGrade().toString();
                bw.write("E," + e.getStudent().getId() + "," + e.getCourse().getCode() + "," + gradeStr);
                bw.newLine();
            }
            System.out.println("Export completed.");
        } catch(Exception e) {
            System.out.println("Export error: "+e.getMessage());
        }
    }
}
