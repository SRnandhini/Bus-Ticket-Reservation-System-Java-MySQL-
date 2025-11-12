import java.sql.*;
import java.util.Scanner;

public class Booking {
    Scanner sc = new Scanner(System.in);

    public void bookTicket() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Passenger Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Bus No: ");
            int busNo = sc.nextInt();

            System.out.print("Enter No. of Seats to Book: ");
            int seats = sc.nextInt();

            String checkQuery = "SELECT available_seats FROM bus WHERE bus_no = ?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setInt(1, busNo);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");
                if (available >= seats) {
                    String insertQuery = "INSERT INTO booking(passenger_name, bus_no, seats_booked) VALUES (?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(insertQuery);
                    ps.setString(1, name);
                    ps.setInt(2, busNo);
                    ps.setInt(3, seats);
                    ps.executeUpdate();

                    String updateQuery = "UPDATE bus SET available_seats = available_seats - ? WHERE bus_no = ?";
                    PreparedStatement ps2 = con.prepareStatement(updateQuery);
                    ps2.setInt(1, seats);
                    ps2.setInt(2, busNo);
                    ps2.executeUpdate();

                    System.out.println("✅ Booking successful for " + name);
                } else {
                    System.out.println("❌ Only " + available + " seats available.");
                }
            } else {
                System.out.println("❌ Invalid Bus Number.");
            }

        } catch (Exception e) {
            System.out.println("Error during booking: " + e.getMessage());
        }
    }

    public void cancelTicket() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Booking ID to Cancel: ");
            int bookingId = sc.nextInt();

            String query = "SELECT bus_no, seats_booked FROM booking WHERE booking_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int busNo = rs.getInt("bus_no");
                int seats = rs.getInt("seats_booked");

                String deleteQuery = "DELETE FROM booking WHERE booking_id = ?";
                PreparedStatement ps2 = con.prepareStatement(deleteQuery);
                ps2.setInt(1, bookingId);
                ps2.executeUpdate();

                String updateQuery = "UPDATE bus SET available_seats = available_seats + ? WHERE bus_no = ?";
                PreparedStatement ps3 = con.prepareStatement(updateQuery);
                ps3.setInt(1, seats);
                ps3.setInt(2, busNo);
                ps3.executeUpdate();

                System.out.println("✅ Ticket canceled successfully!");
            } else {
                System.out.println("❌ Booking ID not found.");
            }

        } catch (Exception e) {
            System.out.println("Error canceling ticket: " + e.getMessage());
        }
    }

    public void viewBookings() {
        String query = "SELECT * FROM booking";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nBooking Details:");
            System.out.println("---------------------------------------------------");
            System.out.printf("%-10s %-20s %-10s %-10s\n", "BookingID", "Passenger", "BusNo", "Seats");
            System.out.println("---------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10d %-10d\n",
                        rs.getInt("booking_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("bus_no"),
                        rs.getInt("seats_booked"));
            }

        } catch (Exception e) {
            System.out.println("Error viewing bookings: " + e.getMessage());
        }
    }
}
