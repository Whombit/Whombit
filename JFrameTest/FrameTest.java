import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class FrameTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JFrame Test - Sprites");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            int width = frame.getWidth();  // Get the frame width
            int height = frame.getHeight(); // Get the frame height

            // Pass width and height to spritePanel constructor
            spritePanel panel = new spritePanel(width, height);
            frame.add(panel);
            frame.setVisible(true);
        });
    }
}

class spritePanel extends JPanel {

    private int width;
    private int height;
    private int x;
    private int y;
    private float velocityX = 0;
    private float velocityY = 0;
    private final float ACCELERATION = 0.5f;
    private final float MAX_SPEED = 5f;
    
    private BufferedImage sprite;  // Declare sprite image
    private JLabel spriteLabel;  // Declare JLabel for sprite

    // Constructor that takes width and height
    public spritePanel(int width, int height) {
        this.width = width;
        this.height = height;

        setFocusable(true);
        setBackground(Color.BLACK);

        // Initialize sprite and spriteLabel
        try {
            sprite = ImageIO.read(new File("Whombit/Whombit/JFrameTest/dot.png"));
        } catch (IOException e) {
            System.out.println("Cannot read image file: " + e.getMessage());
        }
        
        spriteLabel = new JLabel(new ImageIcon(sprite));
        add(spriteLabel);
        
        this.x = width / 2 - sprite.getWidth() / 2;
        this.y = height / 2 - sprite.getHeight() / 2;
        spriteLabel.setBounds(x, y, sprite.getWidth(), sprite.getHeight());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Move sprite based on arrow keys
                if (keyCode == KeyEvent.VK_UP) {
                    velocityY -= ACCELERATION;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    velocityY += ACCELERATION;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    velocityX -= ACCELERATION;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    velocityX += ACCELERATION;
                }

                if (Math.abs(velocityX) > MAX_SPEED) velocityX = Math.signum(velocityX) * MAX_SPEED;
                if (Math.abs(velocityY) > MAX_SPEED) velocityY = Math.signum(velocityY) * MAX_SPEED;

                x += velocityX;
                y += velocityY;

                // Ensure sprite stays within panel bounds
                x = Math.max(0, Math.min(x, width - sprite.getWidth()));
                y = Math.max(0, Math.min(y, height - sprite.getHeight()));

                spriteLabel.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Additional drawing can go here (e.g., map, HUD, etc.)
    }
}
