import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Интерфейс Subject
interface Image {
    void draw(Graphics g, int x, int y);
    void loadFromDisk();
}

// Реальный объект
class RealImage implements Image {
    private String filePath;
    private ImageIcon imageIcon;

    public RealImage(String filePath) {
        this.filePath = filePath;
        loadFromDisk();
    }

    @Override
    public void draw(Graphics g, int x, int y) {
        imageIcon.paintIcon(null, g, x, y);
    }

    @Override
    public void loadFromDisk() {
        imageIcon = new ImageIcon(filePath);
    }
}

// Прокси
class ImageProxy implements Image {
    private RealImage realImage;
    private String filePath;

    public ImageProxy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void draw(Graphics g, int x, int y) {
        if (realImage == null) {
            realImage = new RealImage(filePath);
        }
        realImage.draw(g, x, y);
    }

    @Override
    public void loadFromDisk() {
        if (realImage == null) {
            realImage = new RealImage(filePath);
        }
    }
}

// Графический редактор
class GraphicEditor extends JFrame {
    private Image image;
    private int x, y;

    public GraphicEditor(Image image) {
        this.image = image;
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    image.loadFromDisk();
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        image.draw(g, x, y);
    }
}

// Основной класс с методом main
public class Main {
    public static void main(String[] args) {
        Image image = new ImageProxy("C:\\Users\\Dmitriy\\Pictures\\sCPEtYKE5_KWap40q5OvtcUozUwAFCXm6LJbBxzctwjX7nRQNHJR-VH0wcLwqr5HRMKlbvQHnfwKc__WTaFTBzwo.jpg");
        GraphicEditor editor = new GraphicEditor(image);
        editor.setVisible(true);
    }
}
