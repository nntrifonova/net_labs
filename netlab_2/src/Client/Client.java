package Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Constants.Constants.TIME;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    public static void main(String[] args) {
        /*IP-address & port OR DNS*/
        if (args.length < 3) {
            logger.log(Level.SEVERE, "Invalid number of arguments");
            System.exit(1);
        }
        InetAddress serverAddress = null;
        try {
            serverAddress = InetAddress.getByName(args[1]);
        } catch (UnknownHostException exc) {
            logger.log(Level.SEVERE, "Invalid address passed in arguments to program ");
            System.exit(1);
        }

        Socket socket = new Socket();
        try {
            socket.setSoTimeout(TIME);
            socket.connect(new InetSocketAddress(serverAddress, Integer.parseInt(args[2])));
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Couldn't connect to server.");
            System.exit(1);
        }

        FileSender fileSender = null;
        try {
            fileSender = new FileSender(socket, args[0]);
            fileSender.sentFile();
        } catch (FileNotFoundException exc) {
            logger.log(Level.INFO, "File in address " + args[0] + " is not found!");
            System.exit(1);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
