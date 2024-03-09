import java.io.*;
import java.net.*;
import java.util.*;

public class ExamServer {
    public static void main(String[] args) throws IOException {
        // Create a server socket that listens on port 8000
        ServerSocket serverSocket = new ServerSocket(8000);

        // Wait for a client to connect
        while (true) {
            Socket socket = serverSocket.accept();

            // Create a new thread to handle the connection
            new Thread(new ExamHandler(socket)).start();
        }
    }
}

class ExamHandler implements Runnable {
    private final Socket socket;

    public ExamHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Get the input and output streams
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            // Receive the answer from the client
            String answer = reader.readLine();

            // Process the answer
            // ...

            // Send the remaining time to the client
            int remainingTime = 30; // Calculate the remaining time
            writer.println(remainingTime);

            // Close the streams
            reader.close();
            writer.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}