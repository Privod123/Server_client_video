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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {

    private  int port;
    JTextArea jTextArea;
    private String ipBroadcast;

    private Set<Connection> listIP;

    public Server(int port) {
        this.port = port;
        listIP = new CopyOnWriteArraySet<>();


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
        jTextArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                ipBroadcast = jTextArea.getText();
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(150,150));

        JButton jButton = new JButton(" Смотреть трансляцию ");
        jButton.setBorder(BorderFactory.createLineBorder(Color.black));
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Connection c: listIP) {
                    String currentIP = c.getNewSocket().getInetAddress().getHostAddress() + ":" + c.getNewSocket().getPort();
                    if(currentIP.equals(ipBroadcast)){
                        System.out.println("Запускаем отдельный поток с трансляцией");
                    }
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
        listIP.add(c);
        showListIP(listIP);
        new Thread(new Translation(c.putVideo())).start();
    }

    public void connectionClosed(Connection c){
        listIP.remove(c);
        showListIP(listIP);
        c.close();
    }

    public void connectionEception(Connection c, Exception ex){

    }

    public void resivedConnected(){

    }

    public void showListIP(Set<Connection> listIP){
        jTextArea.selectAll();
        jTextArea.replaceSelection("");
        for (Connection list: listIP) {
            Socket socket = list.getNewSocket();
            jTextArea.append(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            jTextArea.append("\n");
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
