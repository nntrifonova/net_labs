package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        Path uploadsDir = Paths.get("uploads");
        if (!Files.isDirectory(uploadsDir)) {
            try {
                logger.log(Level.INFO, "Creating \"uploads\" directory...");
                Files.createDirectory(uploadsDir);
                logger.log(Level.INFO, "Done");
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                try {
                    Socket socket = serverSocket.accept();//читать
                    Runnable fileReceiver = new FileReceiver(socket);
                    new Thread(fileReceiver).start();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        

    }
}
