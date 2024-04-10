import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Course {
    private Integer courseId;
    private String courseTitle;
    private String courseStartedDate;
    private String courseEndedDate;
    private Boolean isAvailable;

    // Constructor with no parameters
    public Course() {}

    // Constructor with parameters
    public Course(Integer courseId, String courseTitle, String courseStartedDate, String courseEndedDate, Boolean isAvailable) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartedDate = courseStartedDate;
        this.courseEndedDate = courseEndedDate;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartedDate() {
        return courseStartedDate;
    }

    public void setCourseStartedDate(String courseStartedDate) {
        this.courseStartedDate = courseStartedDate;
    }

    public String getCourseEndedDate() {
        return courseEndedDate;
    }

    public void setCourseEndedDate(String courseEndedDate) {
        this.courseEndedDate = courseEndedDate;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
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
                case 1:
                    System.out.print("Enter course title: ");
                    String title = reader.readLine();
                    courseService.addNewCourse(title);
                    break;
                case 2:
                    courseService.getAllCourses();
                    break;
                case 3:
                    System.out.print("Enter course ID: ");
                    int id = Integer.parseInt(reader.readLine());
                    courseService.getCourseById(id);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
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
