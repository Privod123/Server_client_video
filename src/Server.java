import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {

    private  int port;
    JTextArea jTextArea;

    private List<String> listIP = new ArrayList<>();

    public Server(int port) {
        this.port = port;
        JFrame jFrame = new JFrame("Список IP-адресов сервера");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(350,200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTextArea = new JTextArea();

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(200,150));

        jFrame.add(jScrollPane);

        jFrame.setVisible(true);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                Socket socket = serverSocket.accept();
                listIP.add(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                jTextArea.append(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                jTextArea.append("\n");
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            }
        }
    }


    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(Config.getProperti("portServer")));
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
