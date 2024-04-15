import jdk.jfr.DataAmount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Data
class Course {
    private Integer courseId;
    private String courseTitle;
    private String courseStartedDate;
    private String courseEndedDate;
    private Boolean isAvailable;

}

interface CourseService {
    void addNewCourse(String courseTitle);

    void getAllCourses();

    void getCourseById(int courseId);
}

class CourseServiceImpl implements CourseService {
    private List<Course> courses = new ArrayList<>();

    @Override
    public void addNewCourse(String courseTitle) {
        // Generate courseId, dates, and isAvailable
        int courseId = courses.size() + 1;
        String courseStartedDate = "Some Start Date";
        String courseEndedDate = "Some End Date";
        boolean isAvailable = true;

        // Create new course object
        Course newCourse = new Course(courseId, courseTitle, courseStartedDate, courseEndedDate, isAvailable);

        // Add to the list
        courses.add(newCourse);

        // Write to file
        try (PrintWriter writer = new PrintWriter(new FileWriter("course.csv", true))) {
            writer.println(courseId + "," + courseTitle + "," + courseStartedDate + "," + courseEndedDate + "," + isAvailable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllCourses() {
        System.out.println("Course ID | Course Title | Started Date | Ended Date | Available");
        for (Course course : courses) {
            System.out.println(course.getCourseId() + " | " + course.getCourseTitle() + " | " + course.getCourseStartedDate() + " | " + course.getCourseEndedDate() + " | " + course.getAvailable());
        }
    }

    @Override
    public void getCourseById(int courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                System.out.println("Course found:");
                System.out.println("Course ID | Course Title | Started Date | Ended Date | Available");
                System.out.println(course.getCourseId() + " | " + course.getCourseTitle() + " | " + course.getCourseStartedDate() + " | " + course.getCourseEndedDate() + " | " + course.getAvailable());
                return;
            }
        }
        System.out.println("Course not found.");
    }
}

class View {
    public static void menu(CourseService courseService) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add New Course");
            System.out.println("2. Get All Courses");
            System.out.println("3. Search Course by ID");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter course title: ");
                    String title = reader.readLine();
                    courseService.addNewCourse(title);
                }
                case 2 -> courseService.getAllCourses();
                case 3 -> {
                    System.out.print("Enter course ID: ");
                    int id = Integer.parseInt(reader.readLine());
                    courseService.getCourseById(id);
                    break;
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        CourseService courseService = new CourseServiceImpl();
        View.menu(courseService);
    }
}
