import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HotelClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to the server.");

            while (true) {
                displayMenu();
                int choice = Integer.parseInt(userInput.readLine());
                out.println(choice);

                switch (choice) {
                    case 1:
                        reserveRoom(userInput, out, in);
                        break;
                    case 2:
                        cancelReservation(userInput, out, in);
                        break;
                    case 3:
                        viewAvailableRooms(in);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Hotel Reservation System Menu:");
        System.out.println("1. Reserve a room");
        System.out.println("2. Cancel reservation");
        System.out.println("3. View available rooms");
        System.out.println("Enter your choice (1-3): ");
    }

    private static void reserveRoom(BufferedReader userInput, PrintStream out, BufferedReader in) throws IOException {
        System.out.println("Enter the type of room you would like to reserve: ");
        String type = userInput.readLine();
        out.println(type);

        String response;
        while ((response = in.readLine()) != null && !response.isEmpty()) {
            System.out.println(response);
        }

        // Now read the confirmation message after choosing a room number
        response = in.readLine();
        System.out.println(response);

        // Add a prompt to choose another option
        System.out.println("Press Enter to continue...");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }



    private static void cancelReservation(BufferedReader userInput, PrintStream out, BufferedReader in) throws IOException {
        System.out.println("Enter the room number to cancel reservation: ");
        int roomNumber = Integer.parseInt(userInput.readLine());
        out.println(roomNumber);

        String response = in.readLine();
        System.out.println(response);
    }

    private static void viewAvailableRooms(BufferedReader in) throws IOException {
        String roomInfo;
        while ((roomInfo = in.readLine()) != null && !roomInfo.isEmpty()) {
            System.out.println(roomInfo);
        }
        // Add a prompt to choose another option
        System.out.println("Press Enter to continue...");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

}



