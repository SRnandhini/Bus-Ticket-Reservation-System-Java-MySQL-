import java.sql.*;

public class Bus {
    public void viewBuses() {
        String query = "SELECT * FROM bus";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nAvailable Buses:");
            System.out.println("---------------------------------------------------");
            System.out.printf("%-10s %-15s %-15s %-10s %-10s\n", "Bus No", "Source", "Destination", "Total", "Available");
            System.out.println("---------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-15s %-15s %-10d %-10d\n",
                        rs.getInt("bus_no"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats"));
            }

        } catch (Exception e) {
            System.out.println("Error fetching bus details: " + e.getMessage());
        }
    }
}
