import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Translation implements Runnable {

    private Connection connection;
    private JPanel panel;
    private JFrame frame;

    public Translation(Connection c) {
        this.connection = c;
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    @Override
    public void run() {
        while (!connection.getNewSocket().isClosed()){
            InputStream inputStream = new ByteArrayInputStream(connection.putVideo());
            BufferedImage image = null;
            try {
                image = ImageIO.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            panel = new ImageBackgroundPanel(image);
            frame.add(panel);
            frame.validate();
        }
    }
}

class ImageBackgroundPanel extends JPanel {
    BufferedImage image;

    ImageBackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
