import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.Socket;

public class Connection {

    private Socket newSocket;
    private InputStream in;
    private OutputStream out;
    private BufferedOutputStream bout;

    public Connection(Socket socket) {
        this.newSocket = socket;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            bout = new BufferedOutputStream(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendVideo(Webcam webcam){
        //Для отправки данных на сервер
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()){

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
        DataInputStream dis = new DataInputStream(in);
        int len = 0;
        try {
            len = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = new byte[len];

        try {
            len = in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (len == -1) {
            return null;
        }
        while (len < data.length) {
            int read = 0;
            try {
                read = in.read(data, len, data.length - len);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (read == -1) break;
            len += read;
            }

        return data;

    }

    public void close(){

    }

    public Socket getNewSocket() {
        return newSocket;
    }
}
