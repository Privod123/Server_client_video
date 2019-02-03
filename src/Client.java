import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client {

    private SocketAddress socketAddress;

    public Client(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public void start(){
        Socket newSocket = new Socket();
        while (true){
            try {
                newSocket.connect(socketAddress);
                if (newSocket.isConnected()) {
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(new InetSocketAddress(
                                        Config.getProperti("hostServer"),
                                            Integer.parseInt(Config.getProperti("portServer"))));
        client.start();
    }
}
