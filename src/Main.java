import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;  // Flag to control the loop

        while (keepRunning) {
            System.out.println("\nWelcome to the Course Registration System");
            System.out.println("1. Register a new student");
            System.out.println("2. Login as an existing student");
            System.out.println("3. Add a course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:  // Register a new student
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter full name: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    // Call the registration method
                    CourseRegistrationSystem.registerStudent(username, password, fullName, email);
                    break;

                case 2:  // Login as an existing student
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();

                    // Call the login method
                    if (CourseRegistrationSystem.loginStudent(username, password)) {
                        System.out.println("Login successful, proceed with course registration.");
                        int studentId = CourseRegistrationSystem.getStudentId(username);  // Get studentId based on username

                        boolean studentActive = true;

                        while (studentActive) {
                            System.out.println("\n1. View registered courses");
                            System.out.println("2. Register for a course");
                            System.out.println("3. Unregister from a course");
                            System.out.println("4. Logout");
                            System.out.print("Choose an option: ");
                            int action = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline character

                            switch (action) {
                                case 1:  // View registered courses
                                    List<String> registeredCourses = CourseRegistrationSystem.getRegisteredCourses(studentId);
                                    if (registeredCourses.isEmpty()) {
                                        System.out.println("You have not registered for any courses yet.");
                                    } else {
                                        System.out.println("Registered Courses:");
                                        for (String course : registeredCourses) {
                                            System.out.println(course);
                                        }
                                    }
                                    break;

                                case 2:  // Register for a course
                                    System.out.print("Enter course code to register: ");
                                    String courseCode = scanner.nextLine();
                                    boolean success = CourseRegistrationSystem.registerCourse(studentId, courseCode);
                                    if (success) {
                                        System.out.println("Successfully registered for " + courseCode);
                                    } else {
                                        System.out.println("Failed to register for " + courseCode + ". Maybe already registered or invalid course.");
                                    }
                                    break;

                                case 3:  // Unregister from a course
                                    System.out.print("Enter course code to unregister: ");
                                    courseCode = scanner.nextLine();
                                    boolean unregistered = CourseRegistrationSystem.unregisterCourse(studentId, courseCode);
                                    if (unregistered) {
                                        System.out.println("Successfully unregistered from " + courseCode);
                                    } else {
                                        System.out.println("Failed to unregister from " + courseCode + ". You may not be registered for this course.");
                                    }
                                    break;

                                case 4:  // Logout
                                    studentActive = false;
                                    System.out.println("Logged out.");
                                    break;

                                default:
                                    System.out.println("Invalid option. Please choose again.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;
                case 3:
                    // Add a new course
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine();
                
                    CourseRegistrationSystem.addCourse(courseCode, courseName);
                    break;
                case 4:  // Exit the program
                    keepRunning = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }

        scanner.close();  // Close the scanner when done
    }
}
