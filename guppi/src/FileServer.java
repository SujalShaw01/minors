import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) throws IOException {
        // Create a server socket that listens on port 8000
        ServerSocket serverSocket = new ServerSocket(8000);

        // Wait for a client to connect
        while (true) {
            Socket socket = serverSocket.accept();

            // Create a new thread to handle the connection
            new Thread(new FileServerHandler(socket)).start();
        }
    }
}

class FileServerHandler implements Runnable {
    private final Socket socket;

    public FileServerHandler(Socket socket) {
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

            // Receive the file name from the client
            String fileName = reader.readLine();

            // Send the file to the client
            File file = new File(fileName);
            if (file.exists()) {
                byte[] fileData = Files.readAllBytes(file.toPath());
                writer.println(fileData.length);
                output.write(fileData, 0, fileData.length);
            } else {
                writer.println(-1);
            }

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