import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    Socket clientSocket;
    public List<Room> rooms = new ArrayList<>();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        rooms.add(new Room(1, "single", "free", 200));
        rooms.add(new Room(2, "double", "taken", 350));
    }

    @Override
    public void run() {
        try(
                PrintStream out = new PrintStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            while(true){
                int choice = Integer.parseInt(in.readLine());
                switch (choice){
                    case 1:
                        reserveRoom(in, out);
                        break;

                    case 2:
                        cancelReservation(in, out);
                        break;
                    case 3:
                        viewAvailableRooms(out);
                        break;
                    default:
                        System.out.println("Invalid choice from client.");
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private synchronized void reserveRoom(BufferedReader in, PrintStream out) throws IOException {
        out.println("What type of room would you like to reserve?");
        String type = in.readLine();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(type) && room.getStatus().equalsIgnoreCase("free")) {
                out.println("Number: " + room.getNumber() + " Type: " + room.getRoomType());
                out.println("Choose room number: ");
                int num = Integer.parseInt(in.readLine());
                if (room.getNumber() == num && room.getStatus().equalsIgnoreCase("free")) {
                    out.println("Room number " + num + " reserved!");
                    room.setStatus("taken");
                }
                else {
                    out.println("Invalid room selection or the room is already taken.");
                }
                return;
            }
        }
        out.println("No " + type + " room available!");
    }


    private synchronized void cancelReservation(BufferedReader in, PrintStream out) throws IOException{
        out.println("Enter room number: ");
        int num = Integer.parseInt(in.readLine());
        for (Room room : rooms) {
            if (room.getNumber() == num) {
                room.setStatus("free");
                out.println("Reservation canceled!");
                return;
            }
        }
    }

    private synchronized void viewAvailableRooms(PrintStream out) {
        boolean availableRoomsExist = false;
        for (Room room : rooms) {
            if (room.getStatus().equalsIgnoreCase("free")) {
                out.println("Number: " + room.getNumber() + " Type: " + room.getRoomType() +
                        " Status: " + room.getStatus() + " Price: " + room.getPrice());
                availableRoomsExist = true;
            }
        }
        if (!availableRoomsExist) {
            out.println("No available rooms!");
        }
    }
}
