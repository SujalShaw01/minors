import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;

public class RemovableDeviceDetector {
    public static void main(String[] args) throws IOException {
        // Create a watch service for the root directory
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path rootDirectory = Paths.get("");
        rootDirectory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        // Wait for a removable drive to be connected
        WatchKey key = watchService.take();
        List<WatchEvent<?>> events = key.pollEvents();
        for (WatchEvent<?> event : events) {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                Path createdPath = (Path) event.context();
                // Check if the created path is a directory and is a removable drive
                if (Files.isDirectory(createdPath) && isRemovable(createdPath)) {
                    // A removable drive has been connected
                    System.out.println("Removable drive connected at " + createdPath);
                    InetAddress[] hosts = InetAddress.getAllByName(createdPath.getParent().getParent().toString());
                    System.out.println("Host IP address: " + hosts[0]);
                }
            }
        }
        key.reset();
    }

    private static boolean isRemovable(Path path) {
        try {
            FileStore fileStore = Files.getFileStore(path);
            return fileStore.isReadOnly() || (fileStore.type().startsWith("removable") || fileStore.type().startsWith("CD-ROM"));
        } catch (IOException e)
        {private static boolean isRemovable(Path path) {
            try {
                FileStore fileStore = Files.getFileStore(path);
                return fileStore.isReadOnly() || (fileStore.type().startsWith("removable") || fileStore.type().startsWith("CD-ROM"));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }