import java.io.*;
import java.net.*;

public class ExamClient {
    public static void main(String[] args) throws IOException {
        // Connect to the server
        Socket socket = new Socket("localhost", 8000);

        // Get the input and output streams
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // Send the answer to the server
        writer.println("Answer");

        // Receive the remaining time from the server
        int remainingTime = Integer.parseInt(reader.readLine());

        // Print the remaining time
        System.out.println("Remaining time: " + remainingTime);

        // Close the streams
        writer.close();
        reader.close();
        input.close();
        output.close();
        socket.close();
    }
}