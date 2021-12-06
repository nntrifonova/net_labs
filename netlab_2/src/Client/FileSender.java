package Client;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSender {
    private static final Logger logger = Logger.getLogger(FileSender.class.getName());
    private Socket socket;
    private File file;
    private String filename;

    public FileSender(Socket s, String filename) throws FileNotFoundException {
        this.socket = s;
        this.file = new File(filename);
        this.filename = filename;
        if (!this.file.exists()) {
            throw new FileNotFoundException();
        }
    }

    public void sentFile() throws IOException {
        try (FileInputStream input = new FileInputStream(file);
             OutputStream outputStream = socket.getOutputStream()) {

            sentFilenameLength(outputStream);
            sentFilename(outputStream);
            sentFileSize(outputStream);

            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = input.read(buffer)) > -1) {
                outputStream.write(buffer, 0, count);
            }
        } catch (IOException exc) {
            logger.log(Level.INFO, "Error while file sending.");
            throw exc;
        }
    }

   private void sentFilenameLength(OutputStream output) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
        buffer.putShort((short)filename.getBytes(StandardCharsets.UTF_8).length);
        output.write(buffer.array());
    }

    private void sentFilename(OutputStream output) throws IOException {
        output.write(filename.getBytes(StandardCharsets.UTF_8));
    }

    private void sentFileSize(OutputStream output) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(file.length());
        output.write(byteBuffer.array());
    }
}
