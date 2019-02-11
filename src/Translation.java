import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Translation implements Runnable {

    private Connection connection;
    volatile Image image;
    private JFrame frame;

    public Translation(Connection c) {
        this.connection = c;
        frame = new JFrame();
        frame.setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width/2 - 300, dimension.height/2 - 240, 600, 480);
        frame.add(new Component());

    }

    @Override
    public void run() {
        while (!connection.getNewSocket().isClosed()){
            InputStream inputStream = new ByteArrayInputStream(connection.putVideo());
            image = null;
            try {
                image = ImageIO.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* перерисовываем картинку */
            SwingUtilities.invokeLater(() -> frame.repaint());
        }
    }

    class Component extends JComponent{

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(image, 0, 0, null);
        }
    }
}

