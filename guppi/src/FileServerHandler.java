class FileServerHandler implements Runnable {
    private Socket socket;

    public FileServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Get the input and output streams
            InputStream input = socket.getInputStream();
            FileOutputStream output = new FileOutputStream("received_file.txt");

            // Receive the file from the client
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            // Close the streams
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}