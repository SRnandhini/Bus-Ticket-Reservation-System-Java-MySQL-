import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bus bus = new Bus();
        Booking booking = new Booking();

        int choice;
        do {
            System.out.println("\n=== BUS TICKET RESERVATION SYSTEM ===");
            System.out.println("1. View Bus Details");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> bus.viewBuses();
                case 2 -> booking.bookTicket();
                case 3 -> booking.cancelTicket();
                case 4 -> booking.viewBookings();
                case 5 -> System.out.println("Thank you for using the system!");
                default -> System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 5);

        sc.close();
    }
}
