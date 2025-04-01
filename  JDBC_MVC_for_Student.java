public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    static final String URL = "jdbc:mysql://localhost:3306/your_database";
    static final String USER = "root";
    static final String PASSWORD = "your_password";

    public void addStudent(Student student) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            pstmt.executeUpdate();
        }
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student")) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getDouble("Marks")));
            }
        }
        return students;
    }
}
import java.util.List;

public class StudentApp {
    public static void main(String[] args) {
        StudentController controller = new StudentController();

        try {
            // Add Student
            controller.addStudent(new Student(1, "Alice", "CS", 85.5));

            // Retrieve and Display Students
            List<Student> students = controller.getStudents();
            for (Student s : students) {
                System.out.println("ID: " + s.getStudentID() + ", Name: " + s.getName() + ", Dept: " + s.getDepartment() + ", Marks: " + s.getMarks());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
