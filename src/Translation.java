import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Translation implements Runnable {
    private byte[] inputVideo;

    public Translation(byte[] inputVideo) {
        this.inputVideo = inputVideo;
    }

    @Override
    public void run() {
        showTranslation(inputVideo);
    }

    public void showTranslation(byte[] inpit){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 400, 400);
        byte[] bs = inpit;
        InputStream inputStream = new ByteArrayInputStream(bs);
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel panel = new ImageBackgroundPanel(image);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
