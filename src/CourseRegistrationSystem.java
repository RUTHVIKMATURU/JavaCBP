import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationSystem {
    // Register a new student
    public static void registerStudent(String username, String password, String fullName, String email) {
        String query = "INSERT INTO students (username, password, full_name, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // In real applications, store hashed password
            stmt.setString(3, fullName);
            stmt.setString(4, email);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student registered successfully.");
            } else {
                System.out.println("Failed to register student.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Login a student
    public static boolean loginStudent(String username, String password) {
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Compare hashed password in real applications

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;  // Login successful
            } else {
                System.out.println("Invalid username or password.");
                return false;  // Login failed
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    // Register for a course
    public static boolean registerCourse(int studentId, String courseCode) {
        String query = "INSERT INTO registrations (student_id, course_code) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setString(2, courseCode);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    // Unregister from a course
    public static boolean unregisterCourse(int studentId, String courseCode) {
        String query = "DELETE FROM registrations WHERE student_id = ? AND course_code = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setString(2, courseCode);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    // Get a list of courses registered by a student
    public static List<String> getRegisteredCourses(int studentId) {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_code FROM registrations WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("course_code"));
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return courses;
    }
    // Add a course to the system
    public static void addCourse(String courseCode, String courseName) {
        String query = "INSERT INTO courses (course_code, course_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, courseCode);
            stmt.setString(2, courseName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Failed to add course.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Get the studentId by username
    public static int getStudentId(String username) {
        String query = "SELECT student_id FROM students WHERE username = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("student_id");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return -1;  // Return -1 if no student found
    }
}
