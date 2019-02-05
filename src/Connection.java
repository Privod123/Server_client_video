import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
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

    public void sendVideo(Webcam webcam){
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

    public byte[] putVideo(){
        byte[] rez = null;
        try ( ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            DataInputStream dis = new DataInputStream(in);

            byte[] buf = new byte[dis.readInt()];

            int len;
            while ((len = in.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            rez = bos.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return rez;
    }

    public void close(){

    }

    public Socket getNewSocket() {
        return newSocket;
    }
}
