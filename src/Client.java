import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client {

//    private SocketAddress socketAddress;
    private Connection connection;

    public Client(Connection connection) {
        this.connection = connection;
    }

    public void start(){
//        while (true){
//            try {
//                if (newSocket.isConnected()) {
//                    System.out.println("все хорошо");
//                    Thread.sleep(5000);
//                }else {
//                    System.out.println("Пытаюсь подключиться заново");
//                    newSocket.connect(socketAddress);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Webcam webcam = Webcam.getDefault();
//
//            webcam.setViewSize(new Dimension(640, 480)); // устанавливаем размер изображения
//
//            webcam.setViewSize(WebcamResolution.VGA.getSize()); // устанавливаем разрешение
//
//            webcam.open();
//
//            //Для отправки данных на сервер
//            try {
//                OutputStream out = newSocket.getOutputStream();
//
//                BufferedOutputStream bout = new BufferedOutputStream(out);
//
//                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//
//                ImageIO.write(webcam.getImage(), "JPEG", byteOut);
//
//                DataOutputStream dout = new DataOutputStream(bout);
//
//                dout.writeInt(byteOut.size());
//
//                dout.flush();
//
//                bout.write(byteOut.toByteArray());
//            }  catch (IOException e) {
//                e.printStackTrace();
//        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(new Connection(new Socket(
                                                    Config.getProperti("hostServer"),
                                                        Integer.parseInt(Config.getProperti("portServer")))));

        client.start();
    }
}
