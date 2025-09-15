import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDataInserter {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Jayalakshmi7";

    public static void main(String[] args) {
        // SQL statement for inserting data
        String insertSQL = "INSERT INTO employee (empcode, empname, empage, esalary) VALUES (?, ?, ?, ?)";

        // Use a try-with-resources statement to ensure resources are closed automatically
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Data to be inserted
            Object[][] employees = {
                    {101, "Jenny", 25, 10000},
                    {102, "Jacky", 30, 20000},
                    {103, "Joe", 20, 40000},
                    {104, "John", 40, 80000},
                    {105, "Shameer", 25, 90000}
            };

            // Loop through the data and add it to the batch
            for (Object[] employee : employees) {
                preparedStatement.setInt(1, (int) employee[0]);      // empcode
                preparedStatement.setString(2, (String) employee[1]); // empname
                preparedStatement.setInt(3, (int) employee[2]);      // empage
                preparedStatement.setInt(4, (int) employee[3]);      // esalary

                preparedStatement.addBatch(); // Add the current set of parameters to the batch
            }

            // Execute the batch insert
            int[] updateCounts = preparedStatement.executeBatch();

            System.out.println("Data inserted successfully!");
            System.out.println("Number of rows affected: " + updateCounts.length);

        } catch (SQLException e) {
            System.err.println("Database connection or insertion failed!");
            e.printStackTrace();
        }
    }
}