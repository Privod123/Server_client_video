import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {

    private  int port;
    JTextArea jTextArea;
    private String ipBroadcast;

    public static Map<Connection,Boolean> listIP;

    public Server(int port) {
        this.port = port;
        listIP = new HashMap<>();


        JFrame jFrame = new JFrame("Список IP-адресов сервера");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(450,250);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(200,200));
        jPanel1.setOpaque(true);
        JPanel jPanel2 = new JPanel();
        jPanel2.setPreferredSize(new Dimension(150,200));
        jPanel2.setOpaque(true);

        jTextArea = new JTextArea();
        jTextArea.addCaretListener((e) -> ipBroadcast = jTextArea.getText());

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(150,150));

        JButton jButton = new JButton(" Смотреть трансляцию ");
        jButton.setBorder(BorderFactory.createLineBorder(Color.black));
        jButton.addActionListener((e) -> {
            for (Map.Entry<Connection,Boolean> list: listIP.entrySet()) {
                Connection c = list.getKey();
                boolean airON = list.getValue();
                String currentIP = c.getNewSocket().getInetAddress().getHostAddress() + ":" + c.getNewSocket().getPort();
                if(currentIP.trim().equals(ipBroadcast.trim()) && airON == false){
                    list.setValue(true);
                    new Thread(new Translation(c)).start();
                }else {
                    System.out.println("Video broadcast is alredy underway");
                }
            }
        });

        jPanel1.add(jScrollPane);
        jPanel2.add(jButton);
        jFrame.add(jPanel1);
        jFrame.add(jPanel2);

        jFrame.setVisible(true);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connectionCreated(connection);
            }
        }
    }

    public void connectionCreated(Connection c){
        listIP.put(c,false);
        showListIP(listIP);
    }

    public void connectionClosed(Connection c){
        listIP.remove(c);
        showListIP(listIP);
        c.close();
    }


    public void showListIP(Map<Connection,Boolean> listIP){
        jTextArea.selectAll();
        jTextArea.replaceSelection("");
        for (Map.Entry<Connection,Boolean> list: listIP.entrySet()) {
            Socket socket = list.getKey().getNewSocket();
            jTextArea.append(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            jTextArea.append("\n");
        }
    }

    public static void getAirOn (Connection transletion){
        for (Map.Entry<Connection,Boolean> list: listIP.entrySet()) {
            Connection c = list.getKey();
            if (c.equals(transletion)) list.setValue(false);
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
