import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Connection {

    private Socket newSocket;
    private InputStream in;
    private OutputStream out;

    public Connection(Socket socket) {
        this.newSocket = socket;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendVideo(){

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480)); // устанавливаем размер изображения
        webcam.setViewSize(WebcamResolution.VGA.getSize()); // устанавливаем разрешение
        webcam.open();

        //Для отправки данных на сервер
        try (BufferedOutputStream bout = new BufferedOutputStream(out);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream()){

            ImageIO.write(webcam.getImage(), "JPEG", byteOut);

            DataOutputStream dout = new DataOutputStream(bout);
            dout.writeInt(byteOut.size());
            dout.flush();
            bout.write(byteOut.toByteArray());
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){

    }

    public Socket getNewSocket() {
        return newSocket;
    }
}
