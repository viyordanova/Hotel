import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HotelServer {
    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server listening on port 1234...");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        while(true){
            Socket clientSocket = server.accept();
            executor.execute(new ClientHandler(clientSocket));
        }
    }
}
