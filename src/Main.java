import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
        Main window = new Main();
        window.run();
    }

    class Canvas extends JPanel {
        Grid grid = new Grid();
        LinkedList<Point> mouseTrail = new LinkedList<>();  // List to store mouse positions

        public Canvas() {
            setPreferredSize(new Dimension(720, 720));
        }

        @Override
        public void paint(Graphics g) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                mouseTrail.addFirst(mousePos);  // Add current mouse position
                if (mouseTrail.size() > 100) {
                    mouseTrail.removeLast();  // Limit to 100 positions
                }
            }

            // Draw the grid first
            grid.paint(g, mousePos);

            // Draw mouse trails on top of the grid
            g.setColor(new Color(0, 0, 0, 128));  // Semi-transparent black color
            for (Point p : mouseTrail) {
                g.fillOval(p.x - 10, p.y - 10, 20, 20);
            }
        }
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(16);  // Limit to ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}