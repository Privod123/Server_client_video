import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client {

//    private SocketAddress socketAddress;
    private Connection connection;

    public Client(Connection connection) {
        this.connection = connection;
    }

    public void start(){

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480)); // устанавливаем размер изображения
        webcam.setViewSize(WebcamResolution.VGA.getSize()); // устанавливаем разрешение
        webcam.open();

        while (true){
            connection.sendVideo(webcam);
        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(new Connection(new Socket(
                                                    Config.getProperti("hostServer"),
                                                        Integer.parseInt(Config.getProperti("portServer")))));

        client.start();
    }
}
