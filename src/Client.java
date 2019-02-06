import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client {

    private Connection connection;

    public Client(Connection connection) {
        this.connection = connection;
    }

    public void start(){

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480)); // устанавливаем размер изображения
        webcam.setViewSize(WebcamResolution.VGA.getSize()); // устанавливаем разрешение
        webcam.open();

        while (webcam.isOpen()){
            connection.sendVideo(webcam);
            if (connection.getNewSocket().isClosed()) {
                webcam.close();
                System.out.println("webcam closed");
                break;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(new Connection(new Socket(
                                                    Config.getProperti("hostServer"),
                                                        Integer.parseInt(Config.getProperti("portServer")))));

        client.start();
    }
}
