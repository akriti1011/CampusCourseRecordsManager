package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.FileIO;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nCampus Course Records Manager (CCRM)");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Enroll Student");
            System.out.println("4. Record Grades");
            System.out.println("5. Print Transcript");
            System.out.println("6. Import Data");
            System.out.println("7. Export Data");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageCourses(); break;
                case 3: enrollStudent(); break;
                case 4: recordGrades(); break;
                case 5: printTranscript(); break;
                case 6: importData(); break;
                case 7: exportData(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid option"); break;
            }
        }
        System.out.println("Program exited.");
    }

    private static void manageStudents() {
        System.out.println("\nStudents:");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.print("Choose option: ");
        int opt = Integer.parseInt(scanner.nextLine());
        switch (opt) {
            case 1:
                System.out.print("ID: "); String id = scanner.nextLine();
                System.out.print("Name: "); String name = scanner.nextLine();
                System.out.print("Email: "); String email = scanner.nextLine();
                studentService.addStudent(new Student(id, name, email));
                System.out.println("Student added.");
                break;
            case 2:
                studentService.getAllStudents().forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private static void manageCourses() {
        System.out.println("\nCourses:");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.print("Choose option: ");
        int opt = Integer.parseInt(scanner.nextLine());
        switch (opt) {
            case 1:
                System.out.print("Code: "); String code = scanner.nextLine();
                System.out.print("Title: "); String title = scanner.nextLine();
                System.out.print("Credits: "); int credits = Integer.parseInt(scanner.nextLine());
                System.out.print("Semester (SPRING, SUMMER, FALL): "); Semester sem = Semester.valueOf(scanner.nextLine().toUpperCase());
                courseService.addCourse(new Course(code, title, credits, sem));
                System.out.println("Course added.");
                break;
            case 2:
                courseService.getAllCourses().forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private static void enrollStudent() {
        System.out.print("Student ID: "); String sid = scanner.nextLine();
        System.out.print("Course Code: "); String ccode = scanner.nextLine();
        boolean ok = enrollmentService.enroll(sid, ccode);
        System.out.println(ok ? "Enrollment successful." : "Enrollment failed.");
    }

    private static void recordGrades() {
        System.out.print("Student ID: "); String sid = scanner.nextLine();
        System.out.print("Course Code: "); String ccode = scanner.nextLine();
        System.out.print("Grade (A, B, C, D, F): "); Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
        boolean ok = enrollmentService.recordGrade(sid, ccode, grade);
        System.out.println(ok ? "Grade recorded." : "Grade recording failed.");
    }

    private static void printTranscript() {
        System.out.print("Student ID: "); String sid = scanner.nextLine();
        System.out.println(enrollmentService.getTranscript(sid));
    }

    private static void importData() {
        System.out.print("Filename: ");
        String filename = scanner.nextLine();
        FileIO.importData(filename, studentService, courseService, enrollmentService);
    }

    private static void exportData() {
        System.out.print("Filename: ");
        String filename = scanner.nextLine();
        FileIO.exportData(filename, studentService, courseService, enrollmentService);
    }
}
